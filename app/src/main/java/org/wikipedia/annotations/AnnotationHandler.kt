package org.wikipedia.annotations

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.wikipedia.R
import org.wikipedia.annotations.db.PageAnnotation
import org.wikipedia.bridge.CommunicationBridge
import org.wikipedia.bridge.JavaScriptActionHandler
import org.wikipedia.database.AppDatabase
import org.wikipedia.json.JsonUtil
import org.wikipedia.page.PageFragment
import org.wikipedia.page.PageTitle
import org.wikipedia.util.FeedbackUtil
import org.wikipedia.util.log.L

/**
 * Connects the annotations stored in the database with the highlights rendered in the article.
 *
 * When an article loads, its annotations are anchored to the current text of the article by
 * annotations.js. Any annotation that was found in a different place, or with slightly different
 * text (because the article was edited), is updated to match the current revision, so that it
 * continues to track the latest version of the article.
 */
class AnnotationHandler(private val fragment: PageFragment, private val bridge: CommunicationBridge) {
    private val dao get() = AppDatabase.instance.pageAnnotationDao()
    private val exceptionHandler = CoroutineExceptionHandler { _, t -> L.e(t) }
    private val script by lazy {
        fragment.requireContext().assets.open(SCRIPT_ASSET).bufferedReader().use { it.readText() }
    }

    val isEnabled get() = fragment.page != null && !fragment.model.shouldLoadAsMobileWeb

    fun onPageReady() {
        // The callback is invoked once both the page content and metadata are ready, by which
        // point any redirect has been resolved into the final title.
        bridge.evaluate(script) {
            if (fragment.isAdded) {
                loadAnnotations()
            }
        }
    }

    fun annotateSelection(onSelectionReceived: () -> Unit) {
        bridge.evaluate(JavaScriptActionHandler.getAnnotationSelection()) { value ->
            if (!fragment.isAdded) {
                return@evaluate
            }
            onSelectionReceived()
            val selection = JsonUtil.decodeFromString<SelectionInfo>(value)
            if (selection == null || selection.exact.isBlank()) {
                FeedbackUtil.showMessage(fragment, R.string.annotation_selection_error)
                return@evaluate
            }
            fragment.showBottomSheet(AnnotationEditDialog.newInstance(selection.exact, value))
        }
    }

    fun onAnnotationClicked(id: Long) {
        fragment.lifecycleScope.launch(exceptionHandler) {
            val annotation = dao.getById(id) ?: return@launch
            if (fragment.isAdded) {
                fragment.showBottomSheet(AnnotationEditDialog.newInstance(annotation))
            }
        }
    }

    fun onEditResult(result: Bundle) {
        val title = fragment.title ?: return
        val id = result.getLong(AnnotationEditDialog.RESULT_ANNOTATION_ID)
        when (result.getString(AnnotationEditDialog.RESULT_ACTION)) {
            AnnotationEditDialog.ACTION_DELETE -> {
                bridge.execute(JavaScriptActionHandler.removeAnnotation(id))
                MainScope().launch(exceptionHandler) { dao.delete(id) }
                FeedbackUtil.showMessage(fragment, R.string.annotation_deleted)
            }
            AnnotationEditDialog.ACTION_SAVE -> {
                val note = result.getString(AnnotationEditDialog.RESULT_NOTE).orEmpty()
                if (id != 0L) {
                    MainScope().launch(exceptionHandler) {
                        dao.getById(id)?.let { dao.update(it.copy(note = note, modified = System.currentTimeMillis())) }
                    }
                } else {
                    JsonUtil.decodeFromString<SelectionInfo>(result.getString(AnnotationEditDialog.RESULT_SELECTION))?.let {
                        createAnnotation(title, it, note)
                    }
                }
            }
        }
    }

    fun scrollToPendingAnnotation() {
        val id = fragment.model.pendingAnnotationId
        if (id == 0L) {
            return
        }
        fragment.model.pendingAnnotationId = 0
        bridge.evaluate(JavaScriptActionHandler.scrollToAnnotation(id)) { value ->
            if (fragment.isAdded && value != "true") {
                FeedbackUtil.showMessage(fragment, R.string.annotation_not_found)
            }
        }
    }

    private fun createAnnotation(title: PageTitle, selection: SelectionInfo, note: String) {
        MainScope().launch(exceptionHandler) {
            val annotation = PageAnnotation(
                authority = title.wikiSite.authority(),
                lang = title.wikiSite.languageCode,
                namespace = title.namespace,
                apiTitle = title.text,
                displayTitle = title.displayText,
                revision = selection.revision,
                exact = selection.exact,
                prefix = selection.prefix,
                suffix = selection.suffix,
                startOffset = selection.start,
                endOffset = selection.end,
                note = note
            ).let { it.copy(id = dao.insert(it)) }
            if (fragment.isAdded && isCurrentPage(title)) {
                anchor(JavaScriptActionHandler.addAnnotation(JsonUtil.encodeToString(AnchorRequest.from(annotation)).orEmpty()), listOf(annotation), title)
            }
        }
    }

    private fun loadAnnotations() {
        val title = fragment.title ?: return
        fragment.lifecycleScope.launch(exceptionHandler) {
            val annotations = dao.getForPage(title.wikiSite.languageCode, title.namespace, title.text)
            if (!fragment.isAdded || !isCurrentPage(title)) {
                return@launch
            }
            if (annotations.isEmpty()) {
                scrollToPendingAnnotation()
                return@launch
            }
            val requestJson = JsonUtil.encodeToString(annotations.map { AnchorRequest.from(it) }).orEmpty()
            anchor(JavaScriptActionHandler.applyAnnotations(requestJson), annotations, title)
        }
    }

    private fun anchor(js: String, annotations: List<PageAnnotation>, title: PageTitle) {
        bridge.evaluate(js) { value ->
            if (!fragment.isAdded || !isCurrentPage(title)) {
                return@evaluate
            }
            JsonUtil.decodeFromString<AnchorResponse>(value)?.let { saveReanchored(annotations, it) }
            scrollToPendingAnnotation()
        }
    }

    private fun saveReanchored(annotations: List<PageAnnotation>, response: AnchorResponse) {
        val annotationsById = annotations.associateBy { it.id }
        val changed = response.results.mapNotNull { result ->
            val annotation = annotationsById[result.id] ?: return@mapNotNull null
            val updated = if (result.status == STATUS_ORPHANED) {
                annotation.copy(orphaned = true)
            } else {
                annotation.copy(
                    revision = if (response.revision > 0) response.revision else annotation.revision,
                    exact = result.exact,
                    prefix = result.prefix,
                    suffix = result.suffix,
                    startOffset = result.start,
                    endOffset = result.end,
                    orphaned = false
                )
            }
            updated.takeIf { it != annotation }
        }
        if (changed.isNotEmpty()) {
            MainScope().launch(exceptionHandler) { dao.updateAll(changed) }
        }
    }

    private fun isCurrentPage(title: PageTitle): Boolean {
        return fragment.title?.let { it.prefixedText == title.prefixedText && it.wikiSite == title.wikiSite } == true
    }

    @Serializable
    private class SelectionInfo(
        val exact: String = "",
        val prefix: String = "",
        val suffix: String = "",
        val start: Int = 0,
        val end: Int = 0,
        val revision: Long = 0
    )

    @Serializable
    private class AnchorRequest(
        val id: Long,
        val exact: String,
        val prefix: String,
        val suffix: String,
        val start: Int,
        val end: Int
    ) {
        companion object {
            fun from(annotation: PageAnnotation): AnchorRequest {
                return AnchorRequest(annotation.id, annotation.exact, annotation.prefix, annotation.suffix,
                    annotation.startOffset, annotation.endOffset)
            }
        }
    }

    @Serializable
    private class AnchorResult(
        val id: Long = 0,
        val status: String = "",
        val exact: String = "",
        val prefix: String = "",
        val suffix: String = "",
        val start: Int = 0,
        val end: Int = 0
    )

    @Serializable
    private class AnchorResponse(
        val revision: Long = 0,
        val results: List<AnchorResult> = emptyList()
    )

    companion object {
        private const val SCRIPT_ASSET = "annotations.js"
        private const val STATUS_ORPHANED = "orphaned"
    }
}
