package org.wikipedia.server.wikidata;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.StringUtils;
import org.wikipedia.dataclient.mwapi.MwServiceError;
import org.wikipedia.util.log.L;

import java.util.Map;

/**
 * Gson POJO for loading the Wikidata claims for a certain item.
 */
public class WdEntities {
    private MwServiceError error;
    private Map<Object, QItem> entities;

    public boolean hasError() {
        return error != null || entities == null;
    }

    @Nullable
    public MwServiceError getError() {
        return error;
    }

    public void logError(String message) {
        if (error != null) {
            message += ": " + error.toString();
        }
        L.e(message);
    }

    @Nullable
    public Map<Object, QItem> getQItems() {
        if (entities != null) {
            return entities;
        }
        return null;
    }

    @Nullable
    public Map<Object, Claim[]> getClaims() {
        if (entities != null && !entities.isEmpty()) {
            return entities.get(entities.keySet().iterator().next()).claims;
        }
        return null;
    }

    public static class QItem {
        @Nullable private String type;
        @Nullable private String id;
        private Map<Object, Label> labels;
        private Map<Object, Description> descriptions;
        private Map<Object, Claim[]> claims;

        @Nullable public String getId() {
            return id;
        }

        @Nullable public Map<Object, Label> getLabels() {
            return labels;
        }

        @Nullable public String getLabel(@NonNull String lang) {
            if (labels != null) {
                for (Object key : labels.keySet()) {
                    if (key.toString().equals(lang)) {
                        return labels.get(key).value;
                    }
                }
            }
            return null;
        }
    }

    public static class Label {
        @Nullable private String language;
        @Nullable private String value;

        @Nullable public String getValue() {
            return value;
        }
    }

    public static class Description {
        @Nullable private String language;
        @Nullable private String value;
    }

    public static class Claim {
        @Nullable private String type;
        @Nullable private String id;
        @Nullable private String rank;
        @Nullable private Mainsnak mainsnak;

        @Nullable public String getRank() {
            return rank;
        }

        @Nullable public String getType() {
            return type;
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

        @Nullable public String getSnakType() {
            return snaktype;
        }

        @Nullable public String getDataType() {
            return datatype;
        }

        @Nullable public String getProperty() {
            return property;
        }

        @Nullable public DataValue getDataValue() {
            return datavalue;
        }
    }

    public static class DataValue {
        @Nullable private String type;
        @Nullable private JsonElement value;

        @Nullable public String getType() {
            return type;
        }

        @Nullable public JsonElement getValue() {
            return value;
        }
    }

    public static class EntityIdValue {
        @SerializedName("entity-type") @Nullable private String entityType;
        @SerializedName("numeric-id") private int numericId;

        @Nullable public String getEntityType() {
            return entityType;
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

        @Nullable public String getAmount() {
            return amount;
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

        @Nullable public String getText() {
            return text;
        }
    }
}