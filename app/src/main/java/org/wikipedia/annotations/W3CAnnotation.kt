package org.wikipedia.annotations

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.wikipedia.annotations.db.PageAnnotation
import java.time.Instant

/**
 * Serialization of annotations according to the W3C Web Annotation Data Model.
 * https://www.w3.org/TR/annotation-model/
 */
@Serializable
class W3CAnnotationPage(
    @SerialName("@context") val context: String = CONTEXT,
    val type: String = "AnnotationPage",
    val items: List<W3CAnnotation>
)

@Serializable
class W3CAnnotation(
    val id: String,
    val type: String = "Annotation",
    val motivation: String,
    val created: String,
    val modified: String,
    val body: Body? = null,
    val target: Target
) {
    @Serializable
    class Body(
        val type: String = "TextualBody",
        val value: String,
        val format: String = "text/plain"
    )

    @Serializable
    class Target(
        val source: String,
        val selector: List<Selector>
    )

    @Serializable
    class Selector(
        val type: String,
        val exact: String? = null,
        val prefix: String? = null,
        val suffix: String? = null,
        val start: Int? = null,
        val end: Int? = null
    )

    companion object {
        fun from(annotation: PageAnnotation): W3CAnnotation {
            val title = annotation.title
            return W3CAnnotation(
                id = "urn:uuid:${annotation.uuid}",
                motivation = if (annotation.note.isEmpty()) "highlighting" else "commenting",
                created = Instant.ofEpochMilli(annotation.created).toString(),
                modified = Instant.ofEpochMilli(annotation.modified).toString(),
                body = annotation.note.takeIf { it.isNotEmpty() }?.let { Body(value = it) },
                target = Target(
                    source = if (annotation.revision > 0) title.getWebApiUrl("oldid=${annotation.revision}") else title.uri,
                    selector = listOf(
                        Selector(type = "TextQuoteSelector", exact = annotation.exact, prefix = annotation.prefix, suffix = annotation.suffix),
                        Selector(type = "TextPositionSelector", start = annotation.startOffset, end = annotation.endOffset)
                    )
                )
            )
        }
    }
}

private const val CONTEXT = "http://www.w3.org/ns/anno.jsonld"

private val w3cJson = Json {
    prettyPrint = true
    encodeDefaults = true
    explicitNulls = false
}

fun List<PageAnnotation>.toW3CJson(): String {
    return w3cJson.encodeToString(W3CAnnotationPage(items = map { W3CAnnotation.from(it) }))
}
