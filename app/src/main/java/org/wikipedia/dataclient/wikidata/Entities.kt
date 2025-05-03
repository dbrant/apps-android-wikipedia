package org.wikipedia.dataclient.wikidata

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromJsonElement
import org.wikipedia.dataclient.mwapi.MwResponse
import org.wikipedia.json.JsonUtil

@Serializable
class Entities : MwResponse() {

    var entities: Map<String, Entity> = emptyMap()
        private set
    val first: Entity?
        get() = entities.values.firstOrNull()

    init {
        entities = entities.filter { it.key != "-1" && it.value.missing == null }
    }

    @Serializable
    class Entity {
        val id: String = ""
        private val labels: JsonElement? = null
        private val descriptions: JsonElement? = null
        private val sitelinks: JsonElement? = null
        private val statements: JsonElement? = null
        val missing: JsonElement? = null
        @SerialName("lastrevid") val lastRevId: Long = 0

        fun getStatements(): Map<String, List<Claims.Claim>> {
            return if (statements != null && statements !is JsonArray) {
                JsonUtil.json.decodeFromJsonElement(statements)
            } else {
                emptyMap()
            }
        }

        val claims: Map<String, List<Claim>> = emptyMap()

        fun getLabelForLang(lang: String): String {
            return getLabels()[lang]?.value.orEmpty()
        }

        fun getLabels(): Map<String, Label> {
            return if (labels != null && labels !is JsonArray) {
                JsonUtil.json.decodeFromJsonElement(labels)
            } else {
                emptyMap()
            }
        }

        fun getDescriptions(): Map<String, Label> {
            return if (descriptions != null && descriptions !is JsonArray) {
                JsonUtil.json.decodeFromJsonElement(descriptions)
            } else {
                emptyMap()
            }
        }

        fun getSiteLinks(): Map<String, SiteLink> {
            return if (sitelinks != null && sitelinks !is JsonArray) {
                JsonUtil.json.decodeFromJsonElement(sitelinks)
            } else {
                emptyMap()
            }
        }
    }

    @Serializable
    class Label {
        val language: String = ""
        val value: String = ""
    }

    @Serializable
    class SiteLink {
        val site: String = ""
        val title: String = ""
    }

    @Serializable
    class Claim {
        val type: String? = null
            get() = field.orEmpty()

        private val id: String = ""
        val rank: String = ""
        val mainsnak: Mainsnak? = null
    }

    @Serializable
    class Mainsnak {
        val snaktype: String = ""
        val datatype: String = ""
        val property: String = ""
        val datavalue: DataValue? = null
    }

    @Serializable
    class DataValue {
        val type: String = ""
        val value: JsonElement? = null
    }

    @Serializable
    class EntityIdValue {
        @SerialName("entity-type")
        val entityType: String = ""
        @SerialName("numeric-id")
        val numericId = 0
    }

    @Serializable
    class QuantityValue {
        val amount: String = ""
        val unit: String = ""
        val lowerBound: String = ""
        val upperBound: String = ""
    }

    @Serializable
    class TimeValue {
        val time: String = ""
        private val timezone = 0
        private val before = 0
        private val after = 0
        val precision = 0
        val calendarModel: String = ""
    }

    @Serializable
    class LocationValue {
        val latitude = 0f
        val longitude = 0f
        private val altitude = 0f
        private val precision = 0f
    }

    @Serializable
    class MonolingualTextValue {
        private val language: String? = null
        val text: String = ""
    }
}
