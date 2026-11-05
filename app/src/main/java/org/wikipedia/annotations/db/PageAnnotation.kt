package org.wikipedia.annotations.db

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import org.wikipedia.dataclient.WikiSite
import org.wikipedia.page.PageTitle
import java.util.UUID

/**
 * A highlighted passage of an article, with an optional note. The passage is identified by a
 * text quote (exact, prefix, suffix) and its character offsets within the article text, as of
 * the given revision. These map directly to the TextQuoteSelector and TextPositionSelector of
 * the W3C Web Annotation model.
 */
@Entity(
    indices = [Index(value = ["lang", "namespace", "apiTitle"])]
)
data class PageAnnotation(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val uuid: String = UUID.randomUUID().toString(),
    val authority: String,
    val lang: String,
    val namespace: String,
    val apiTitle: String,
    val displayTitle: String,
    val revision: Long,
    val exact: String,
    val prefix: String,
    val suffix: String,
    val startOffset: Int,
    val endOffset: Int,
    val note: String = "",
    val created: Long = System.currentTimeMillis(),
    val modified: Long = created,
    // True when the passage could not be found in the most recently loaded revision of the article.
    val orphaned: Boolean = false
) {
    val title: PageTitle get() = PageTitle(namespace, apiTitle, WikiSite(authority, lang)).also {
        it.displayText = displayTitle
    }
}
