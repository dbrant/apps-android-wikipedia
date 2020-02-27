package org.wikipedia.dataclient.wikidata;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.StringUtils;
import org.wikipedia.dataclient.mwapi.MwResponse;
import org.wikipedia.json.PostProcessingTypeAdapter;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class Entities extends MwResponse implements PostProcessingTypeAdapter.PostProcessable {
    @Nullable private Map<String, Entity> entities;
    private int success;

    @NonNull public Map<String, Entity> entities() {
        return entities != null ? entities : Collections.emptyMap();
    }

    @Nullable public Entity getFirst() {
        if (entities == null) {
            return null;
        }
        return entities.values().iterator().next();
    }

    @Override
    public void postProcess() {
        if (getFirst() != null && getFirst().isMissing()) {
            throw new RuntimeException("The requested entity was not found.");
        }
    }

    public static class Entity {
        @Nullable private String type;
        @Nullable private String id;
        @Nullable private Map<String, Label> labels;
        @Nullable private Map<String, Label> descriptions;
        @Nullable private Map<String, SiteLink> sitelinks;
        @Nullable private Map<String, List<Claim>> claims;
        @Nullable private String missing;
        private long lastrevid;

        @NonNull public String id() {
            return StringUtils.defaultString(id);
        }

        @NonNull public Map<String, Label> labels() {
            return labels != null ? labels : Collections.emptyMap();
        }

        @NonNull public Map<String, Label> descriptions() {
            return descriptions != null ? descriptions : Collections.emptyMap();
        }

        @NonNull public Map<String, SiteLink> sitelinks() {
            return sitelinks != null ? sitelinks : Collections.emptyMap();
        }

        @NonNull public Map<String, List<Claim>> claims() {
            return claims != null ? claims : Collections.emptyMap();
        }

        @NonNull public String getLabelForLang(@NonNull String lang) {
            return (labels != null && labels.containsKey(lang)) ? labels.get(lang).value() : "";
        }

        public boolean isMissing() {
            return "-1".equals(id) && missing != null;
        }

        public long getLastRevId() {
            return lastrevid;
        }
    }

    public static class Label {
        @Nullable private String language;
        @Nullable private String value;

        @NonNull public String language() {
            return StringUtils.defaultString(language);
        }

        @NonNull public String value() {
            return StringUtils.defaultString(value);
        }
    }

    public static class SiteLink {
        @Nullable private String site;
        @Nullable private String title;

        @NonNull public String getSite() {
            return StringUtils.defaultString(site);
        }

        @NonNull public String getTitle() {
            return StringUtils.defaultString(title);
        }
    }

    public static class Claim {
        @Nullable private String type;
        @Nullable private String id;
        @Nullable private String rank;
        @Nullable private Mainsnak mainsnak;

        @NonNull public String getRank() {
            return StringUtils.defaultString(rank);
        }

        @NonNull public String getType() {
            return StringUtils.defaultString(type);
        }

        @Nullable public Mainsnak getMainsnak() {
            return mainsnak;
        }
    }

    public static class Mainsnak {
        @Nullable private String snaktype;
        @Nullable private String datatype;
        @Nullable private String property;
        @Nullable private DataValue datavalue;

        @NonNull public String getSnakType() {
            return StringUtils.defaultString(snaktype);
        }

        @NonNull public String getDataType() {
            return StringUtils.defaultString(datatype);
        }

        @NonNull public String getProperty() {
            return StringUtils.defaultString(property);
        }

        @Nullable public DataValue getDataValue() {
            return datavalue;
        }
    }

    public static class DataValue {
        @Nullable private String type;
        @Nullable private JsonElement value;

        @NonNull public String getType() {
            return StringUtils.defaultString(type);
        }

        @Nullable public JsonElement getValue() {
            return value;
        }
    }

    public static class EntityIdValue {
        @SerializedName("entity-type") @Nullable private String entityType;
        @SerializedName("numeric-id") private int numericId;

        @NonNull public String getEntityType() {
            return StringUtils.defaultString(entityType);
        }

        public int getNumericId() {
            return numericId;
        }
    }

    public static class QuantityValue {
        @Nullable private String amount;
        @Nullable private String unit;
        @Nullable private String lowerBound;
        @Nullable private String upperBound;

        @NonNull public String getAmount() {
            return StringUtils.defaultString(amount);
        }
    }

    public static class TimeValue {
        @Nullable private String time;
        private int timezone;
        private int before;
        private int after;
        private int precision;
        @Nullable private String calendarModel;

        @NonNull public String getTime() {
            return StringUtils.defaultString(time);
        }

        public int getPrecision() {
            return precision;
        }
    }

    public static class LocationValue {
        private float latitude;
        private float longitude;
        private float altitude;
        private float precision;

        public float getLatitude() {
            return latitude;
        }

        public float getLongitude() {
            return longitude;
        }
    }

    public static class MonolingualTextValue {
        @Nullable private String language;
        @Nullable private String text;

        @NonNull public String getText() {
            return StringUtils.defaultString(text);
        }
    }
}
