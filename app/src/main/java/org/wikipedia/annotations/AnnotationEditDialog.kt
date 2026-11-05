package org.wikipedia.annotations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import org.wikipedia.R
import org.wikipedia.annotations.db.PageAnnotation
import org.wikipedia.compose.ComposeColors
import org.wikipedia.compose.components.AppButton
import org.wikipedia.compose.components.AppTextButton
import org.wikipedia.compose.theme.BaseTheme
import org.wikipedia.compose.theme.WikipediaTheme
import org.wikipedia.page.ExtendedBottomSheetDialogFragment
import org.wikipedia.theme.Theme

/**
 * Bottom sheet for adding a note to a newly highlighted passage, or for editing or deleting an
 * existing annotation. The outcome is delivered as a fragment result under [REQUEST_KEY].
 */
class AnnotationEditDialog : ExtendedBottomSheetDialogFragment(startExpanded = true) {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val args = requireArguments()
        val annotationId = args.getLong(ARG_ANNOTATION_ID)
        return ComposeView(requireContext()).apply {
            setContent {
                BaseTheme {
                    AnnotationEditContent(
                        quote = args.getString(ARG_QUOTE).orEmpty(),
                        initialNote = args.getString(ARG_NOTE).orEmpty(),
                        isNew = annotationId == 0L,
                        onSave = { note ->
                            setFragmentResult(REQUEST_KEY, bundleOf(
                                RESULT_ACTION to ACTION_SAVE,
                                RESULT_ANNOTATION_ID to annotationId,
                                RESULT_NOTE to note,
                                RESULT_SELECTION to args.getString(ARG_SELECTION)
                            ))
                            dismiss()
                        },
                        onDelete = {
                            setFragmentResult(REQUEST_KEY, bundleOf(
                                RESULT_ACTION to ACTION_DELETE,
                                RESULT_ANNOTATION_ID to annotationId
                            ))
                            dismiss()
                        }
                    )
                }
            }
        }
    }

    companion object {
        const val REQUEST_KEY = "annotationEdit"
        const val RESULT_ACTION = "action"
        const val RESULT_ANNOTATION_ID = "annotationId"
        const val RESULT_NOTE = "note"
        const val RESULT_SELECTION = "selection"
        const val ACTION_SAVE = "save"
        const val ACTION_DELETE = "delete"

        private const val ARG_ANNOTATION_ID = "annotationId"
        private const val ARG_QUOTE = "quote"
        private const val ARG_NOTE = "note"
        private const val ARG_SELECTION = "selection"

        fun newInstance(quote: String, selectionJson: String): AnnotationEditDialog {
            return AnnotationEditDialog().apply {
                arguments = bundleOf(ARG_QUOTE to quote, ARG_SELECTION to selectionJson)
            }
        }

        fun newInstance(annotation: PageAnnotation): AnnotationEditDialog {
            return AnnotationEditDialog().apply {
                arguments = bundleOf(
                    ARG_ANNOTATION_ID to annotation.id,
                    ARG_QUOTE to annotation.exact,
                    ARG_NOTE to annotation.note
                )
            }
        }
    }
}

@Composable
fun AnnotationQuote(
    quote: String,
    modifier: Modifier = Modifier,
    maxLines: Int = 6
) {
    Row(modifier = modifier.height(IntrinsicSize.Min)) {
        Box(
            modifier = Modifier
                .width(3.dp)
                .fillMaxHeight()
                .background(ComposeColors.Yellow500)
        )
        Text(
            text = quote,
            modifier = Modifier.padding(start = 12.dp),
            style = MaterialTheme.typography.bodyLarge.copy(fontStyle = FontStyle.Italic),
            color = WikipediaTheme.colors.secondaryColor,
            maxLines = maxLines,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun AnnotationEditContent(
    quote: String,
    initialNote: String,
    isNew: Boolean,
    onSave: (String) -> Unit,
    onDelete: () -> Unit
) {
    var note by rememberSaveable { mutableStateOf(initialNote) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(WikipediaTheme.colors.paperColor)
            .imePadding()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(if (isNew) R.string.annotation_edit_title_new else R.string.annotation_edit_title),
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 20.sp, fontWeight = FontWeight.Bold),
            color = WikipediaTheme.colors.primaryColor
        )
        AnnotationQuote(
            quote = quote,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        OutlinedTextField(
            value = note,
            onValueChange = { note = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = stringResource(R.string.annotation_note_hint),
                    color = WikipediaTheme.colors.placeholderColor
                )
            },
            minLines = 3,
            maxLines = 8,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = WikipediaTheme.colors.primaryColor,
                unfocusedTextColor = WikipediaTheme.colors.primaryColor,
                focusedBorderColor = WikipediaTheme.colors.progressiveColor,
                unfocusedBorderColor = WikipediaTheme.colors.borderColor,
                cursorColor = WikipediaTheme.colors.progressiveColor
            )
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (!isNew) {
                AppTextButton(
                    onClick = onDelete,
                    contentColor = WikipediaTheme.colors.destructiveColor
                ) {
                    Text(text = stringResource(R.string.annotation_delete))
                }
                Spacer(modifier = Modifier.weight(1f))
            }
            AppButton(onClick = { onSave(note.trim()) }) {
                Text(text = stringResource(R.string.annotation_save))
            }
        }
    }
}

@Preview
@Composable
private fun AnnotationEditContentPreview() {
    BaseTheme(currentTheme = Theme.LIGHT) {
        AnnotationEditContent(
            quote = "The quick brown fox jumps over the lazy dog, a sentence that contains every letter of the alphabet.",
            initialNote = "A pangram!",
            isNew = false,
            onSave = {},
            onDelete = {}
        )
    }
}
