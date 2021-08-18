package org.wikipedia.dataclient.wikidata

import com.google.gson.annotations.SerializedName
import org.wikipedia.dataclient.mwapi.MwResponse
import org.wikipedia.json.PostProcessingTypeAdapter.PostProcessable
import com.google.gson.JsonElement




class Entities : MwResponse(), PostProcessable {

    val entities: Map<String, Entity> = emptyMap()
    val first: Entity?
        get() = if (entities.isEmpty()) null else entities.values.iterator().next()

    override fun postProcess() {
        if (first?.isMissing == true) {
            throw RuntimeException("The requested entity was not found.")
        }
    }

    class Entity {

        private val id: String = ""
        val labels: Map<String, Label> = emptyMap()
        val descriptions: Map<String, Label> = emptyMap()
        val sitelinks: Map<String, SiteLink> = emptyMap()
        val claims: Map<String, List<Claim>> = emptyMap()
        @SerializedName("missing")
        val isMissing: Boolean? = null
            get() = "-1" == id && field != null
        val lastRevId: Long = 0

        fun getLabelForLang(lang: String): String {
            return labels[lang]?.value.orEmpty()
        }
    }

    class Label {
        val language: String = ""
        val value: String = ""
    }

    class SiteLink {
        val site: String = ""
        val title: String = ""
    }

    class Claim {
        val type: String? = null
            get() = field.orEmpty()

        private val id: String = ""
        val rank: String = ""
        val mainsnak: Mainsnak? = null
    }

    class Mainsnak {
        val snaktype: String = ""
        val datatype: String = ""
        val property: String = ""
        val datavalue: DataValue? = null
    }

    class DataValue {
        val type: String = ""
        val value: JsonElement? = null
    }

    class EntityIdValue {
        @SerializedName("entity-type")
        val entityType: String = ""
        @SerializedName("numeric-id")
        val numericId = 0
    }

    class QuantityValue {
        val amount: String = ""
        val unit: String = ""
        val lowerBound: String = ""
        val upperBound: String = ""
    }

    class TimeValue {
        val time: String = ""
        private val timezone = 0
        private val before = 0
        private val after = 0
        val precision = 0
        val calendarModel: String = ""
    }

    class LocationValue {
        val latitude = 0f
        val longitude = 0f
        private val altitude = 0f
        private val precision = 0f
    }

    class MonolingualTextValue {
        private val language: String? = null
        val text: String = ""
    }
}
