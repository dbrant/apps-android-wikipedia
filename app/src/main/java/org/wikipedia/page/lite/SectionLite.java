package org.wikipedia.page.lite;

import android.support.annotation.Nullable;

import org.wikipedia.data.GsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.annotations.Expose;

import java.util.List;

import static org.wikipedia.util.StringUtil.compareStrings;

/**
 * Gson POJO for one section of a page.
 */
public class SectionLite {

    @Expose private int id;
    @Expose private int toclevel = 1;
    @Expose private String line;
    @Expose private String anchor;
    @Expose @Nullable private List<Item> items;

    /** Default constructor used by Gson deserialization. Good for setting default values. */
    public SectionLite() {
        toclevel = 1;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SectionLite)) {
            return false;
        }

        SectionLite other = (SectionLite) o;
        return getId() == other.getId()
                && getLevel() == other.getLevel()
                && compareStrings(getHeading(), other.getHeading())
                && compareStrings(getAnchor(), other.getAnchor());
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getHeading().hashCode();
        result = 31 * result + getAnchor().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Section{"
                + "id=" + id
                + ", toclevel=" + toclevel
                + ", line='" + line + '\''
                + ", anchor='" + anchor + '\''
                + '}';
    }

    public boolean isLead() {
        return id == 0;
    }

    public int getId() {
        return id;
    }

    public int getLevel() {
        return toclevel;
    }

    public String getHeading() {
        return line;
    }

    public String getAnchor() {
        return anchor;
    }

    public List<Item> getItems() {
        return items;
    }

    public static class Item {
        @Expose private String type;
        @Expose private String text;
        @Expose private String name;
        @Expose private String src;
        @Expose private String caption;

        public String getType() {
            return type;
        }
        public String getText() {
            return text;
        }
        public String getName() {
            return name;
        }
        public String getSrc() {
            return src;
        }
        public String getCaption() {
            return caption;
        }
    }
}
