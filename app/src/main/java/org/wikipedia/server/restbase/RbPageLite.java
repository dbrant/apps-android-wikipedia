package org.wikipedia.server.restbase;

import org.wikipedia.page.PageProperties;
import org.wikipedia.page.PageTitle;
import org.wikipedia.page.Section;
import org.wikipedia.page.lite.SectionLite;
import org.wikipedia.server.PageLeadProperties;
import org.wikipedia.server.PageLite;
import org.wikipedia.util.UriUtil;
import org.wikipedia.util.log.L;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.reflect.Type;
import java.util.List;

import static org.wikipedia.util.StringUtil.capitalizeFirstChar;

/**
 * Gson POJO for loading the first stage of page content.
 */
public class RbPageLite implements PageLite, PageLeadProperties {
    @Expose private RbServiceError error;
    @Expose private int id;
    @Expose private long revision;
    @Expose @Nullable private String lastmodified;
    @Expose @Nullable private String displaytitle;
    @Expose @Nullable private String redirected;
    @Expose @Nullable private String normalizedtitle;
    @Expose @Nullable private String extract;
    @Expose @Nullable @SerializedName("pronunciation") private TitlePronunciation titlePronunciation;
    @Expose private int languagecount;
    @Expose private boolean editable;
    @Expose private boolean mainpage;
    @Expose private boolean disambiguation;
    @Expose @Nullable private String description;
    @Expose @Nullable private Thumb thumb;
    @Expose @Nullable private Image image;
    @Expose @Nullable private List<SectionLite> sections;

    private transient int leadImageThumbWidth;

    @Override
    public boolean hasError() {
        return error != null || sections == null;
    }

    @Nullable
    public RbServiceError getError() {
        return error;
    }

    public void logError(String message) {
        if (error != null) {
            message += ": " + error.toString();
        }
        L.e(message);
    }

    @Override
    public List<SectionLite> getSectionsLite() {
        return sections;
    }

    /* package */ PageTitle adjustPageTitle(PageTitle title) {
        if (redirected != null) {
            // Handle redirects properly.
            title = new PageTitle(redirected, title.getSite(), title.getThumbUrl());
        } else if (normalizedtitle != null) {
            // We care about the normalized title only if we were not redirected
            title = new PageTitle(normalizedtitle, title.getSite(), title.getThumbUrl());
        }
        title.setDescription(description);
        return title;
    }

    public String getLeadSectionContent() {
        return "";
    }

    /** Converter */
    public PageProperties toPageProperties() {
        return new PageProperties(this);
    }

    public int getId() {
        return id;
    }

    public long getRevision() {
        return revision;
    }

    @Nullable
    public String getLastModified() {
        return lastmodified;
    }

    @Nullable
    public String getExtract() {
        return extract;
    }

    @Override
    @Nullable
    public String getTitlePronunciationUrl() {
        return titlePronunciation == null
                ? null
                : UriUtil.resolveProtocolRelativeUrl(titlePronunciation.getUrl());
    }

    public int getLanguageCount() {
        return languagecount;
    }

    @Nullable
    public String getDisplayTitle() {
        return displaytitle;
    }

    @Nullable
    public String getRedirected() {
        return redirected;
    }

    @Nullable
    public String getNormalizedTitle() {
        return normalizedtitle;
    }

    @Nullable
    public String getDescription() {
        return description != null ? capitalizeFirstChar(description) : null;
    }

    @Nullable
    public String getLeadImageUrl() {
        return thumb != null ? thumb.getUrl() : null;
    }

    @Nullable
    public String getLeadImageName() {
        return image != null ? image.getName() : null;
    }

    @Nullable
    public String getFirstAllowedEditorRole() {
        return null;
    }

    public boolean isEditable() {
        return editable;
    }

    public boolean isMainPage() {
        return mainpage;
    }

    public boolean isDisambiguation() {
        return disambiguation;
    }

    @Nullable
    public List<Section> getSections() {
        return null;
    }

    public void setLeadImageThumbWidth(int leadImageThumbWidth) {
        this.leadImageThumbWidth = leadImageThumbWidth;
    }

    /**
     * For the lead image File: page name
     */
    public static class TitlePronunciation {
        @Expose @NonNull private String url;

        @NonNull
        public String getUrl() {
            return url;
        }
    }

    /**
     * For the lead image filename
     */
    public static class Image {
        @Expose private int width;
        @Expose private int height;
        @Expose private String name;

        public String getName() {
            return name;
        }
    }

    /**
     * For the lead image thumbnail
     */
    public static class Thumb {
        @Expose private int width;
        @Expose private int height;
        @Expose private String url;

        public String getUrl() {
            return url;
        }
    }

}
