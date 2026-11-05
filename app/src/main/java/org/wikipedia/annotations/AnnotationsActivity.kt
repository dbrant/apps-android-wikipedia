package org.wikipedia.annotations

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.wikipedia.R
import org.wikipedia.activity.BaseActivity
import org.wikipedia.annotations.db.PageAnnotation
import org.wikipedia.compose.components.HtmlText
import org.wikipedia.compose.components.WikiTopAppBar
import org.wikipedia.compose.theme.BaseTheme
import org.wikipedia.compose.theme.WikipediaTheme
import org.wikipedia.history.HistoryEntry
import org.wikipedia.page.PageActivity
import org.wikipedia.theme.Theme
import org.wikipedia.util.DateUtil
import org.wikipedia.util.FeedbackUtil
import org.wikipedia.util.ShareUtil
import java.util.Date

class AnnotationsActivity : BaseActivity() {
    private val viewModel: AnnotationsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BaseTheme {
                val annotations by viewModel.annotations.collectAsState()
                AnnotationsScreen(
                    annotations = annotations,
                    onNavigateBack = { finish() },
                    onAnnotationClick = { openAnnotation(it) },
                    onDeleteClick = { deleteAnnotation(it) },
                    onExportClick = { exportAnnotations() }
                )
            }
        }
    }

    private fun openAnnotation(annotation: PageAnnotation) {
        val entry = HistoryEntry(annotation.title, HistoryEntry.SOURCE_ANNOTATIONS)
        startActivity(PageActivity.newIntentForCurrentTab(this, entry, entry.title, squashBackstack = false)
            .putExtra(PageActivity.EXTRA_ANNOTATION_ID, annotation.id))
    }

    private fun deleteAnnotation(annotation: PageAnnotation) {
        viewModel.delete(annotation)
        FeedbackUtil.makeSnackbar(this, getString(R.string.annotation_deleted))
            .setAction(R.string.annotation_delete_undo) { viewModel.restore(annotation) }
            .show()
    }

    private fun exportAnnotations() {
        lifecycleScope.launch {
            ShareUtil.shareText(this@AnnotationsActivity, getString(R.string.annotations_title), viewModel.exportAsW3CJson())
        }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, AnnotationsActivity::class.java)
        }
    }
}

@Composable
fun AnnotationsScreen(
    annotations: List<PageAnnotation>?,
    onNavigateBack: () -> Unit,
    onAnnotationClick: (PageAnnotation) -> Unit,
    onDeleteClick: (PageAnnotation) -> Unit,
    onExportClick: () -> Unit
) {
    Scaffold(
        containerColor = WikipediaTheme.colors.paperColor,
        topBar = {
            WikiTopAppBar(
                title = stringResource(R.string.annotations_title),
                onNavigationClick = onNavigateBack,
                actions = {
                    if (!annotations.isNullOrEmpty()) {
                        IconButton(onClick = onExportClick) {
                            Icon(
                                painter = painterResource(R.drawable.ic_share),
                                tint = WikipediaTheme.colors.primaryColor,
                                contentDescription = stringResource(R.string.annotations_export)
                            )
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        when {
            annotations == null -> {}
            annotations.isEmpty() -> AnnotationsEmptyView(Modifier.padding(paddingValues))
            else -> {
                // Group by article, keeping articles in order of their most recently modified annotation.
                val annotationsByArticle = annotations.groupBy { Triple(it.lang, it.namespace, it.apiTitle) }.values
                LazyColumn(contentPadding = paddingValues) {
                    annotationsByArticle.forEach { articleAnnotations ->
                        item(key = "header-${articleAnnotations.first().id}") {
                            HtmlText(
                                text = articleAnnotations.first().displayTitle,
                                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 4.dp),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = WikipediaTheme.colors.primaryColor,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                        items(articleAnnotations, key = { it.id }) { annotation ->
                            AnnotationListItem(
                                annotation = annotation,
                                onClick = { onAnnotationClick(annotation) },
                                onDeleteClick = { onDeleteClick(annotation) }
                            )
                            HorizontalDivider(color = WikipediaTheme.colors.borderColor)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AnnotationListItem(
    annotation: PageAnnotation,
    onClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(start = 16.dp, top = 12.dp, bottom = 12.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            AnnotationQuote(quote = annotation.exact, maxLines = 4)
            if (annotation.note.isNotEmpty()) {
                Text(
                    text = annotation.note,
                    style = MaterialTheme.typography.bodyLarge,
                    color = WikipediaTheme.colors.primaryColor
                )
            }
            if (annotation.orphaned) {
                Text(
                    text = stringResource(R.string.annotation_orphaned_label),
                    style = MaterialTheme.typography.bodySmall,
                    color = WikipediaTheme.colors.warningColor
                )
            }
            Text(
                text = DateUtil.getShortDateString(Date(annotation.modified)),
                style = MaterialTheme.typography.bodySmall,
                color = WikipediaTheme.colors.placeholderColor
            )
        }
        IconButton(onClick = onDeleteClick) {
            Icon(
                painter = painterResource(R.drawable.ic_delete_white_24dp),
                tint = WikipediaTheme.colors.secondaryColor,
                contentDescription = stringResource(R.string.annotations_delete_content_description)
            )
        }
    }
}

@Composable
private fun AnnotationsEmptyView(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_annotation_24dp),
            tint = WikipediaTheme.colors.placeholderColor,
            contentDescription = null,
            modifier = Modifier.size(64.dp)
        )
        Text(
            text = stringResource(R.string.annotations_empty_title),
            modifier = Modifier.padding(top = 16.dp),
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 20.sp, fontWeight = FontWeight.Bold),
            color = WikipediaTheme.colors.primaryColor,
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(R.string.annotations_empty_message),
            modifier = Modifier.padding(top = 8.dp),
            style = MaterialTheme.typography.bodyLarge,
            color = WikipediaTheme.colors.secondaryColor,
            textAlign = TextAlign.Center
        )
    }
}

private val previewAnnotations = listOf(
    PageAnnotation(id = 1, authority = "en.wikipedia.org", lang = "en", namespace = "", apiTitle = "Fox",
        displayTitle = "Fox", revision = 1, exact = "Foxes are small to medium-sized, omnivorous mammals.",
        prefix = "", suffix = "", startOffset = 0, endOffset = 52, note = "Omnivorous, not carnivorous!"),
    PageAnnotation(id = 2, authority = "en.wikipedia.org", lang = "en", namespace = "", apiTitle = "Fox",
        displayTitle = "Fox", revision = 1, exact = "a flattened skull, upright, triangular ears",
        prefix = "", suffix = "", startOffset = 100, endOffset = 143, orphaned = true)
)

@Preview
@Composable
private fun AnnotationsScreenPreview() {
    BaseTheme(currentTheme = Theme.LIGHT) {
        AnnotationsScreen(
            annotations = previewAnnotations,
            onNavigateBack = {},
            onAnnotationClick = {},
            onDeleteClick = {},
            onExportClick = {}
        )
    }
}

@Preview
@Composable
private fun AnnotationsScreenEmptyPreview() {
    BaseTheme(currentTheme = Theme.DARK) {
        AnnotationsScreen(
            annotations = emptyList(),
            onNavigateBack = {},
            onAnnotationClick = {},
            onDeleteClick = {},
            onExportClick = {}
        )
    }
}
