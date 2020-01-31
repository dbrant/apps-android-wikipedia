package org.wikipedia.analytics;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;
import org.wikipedia.Constants.InvokeSource;
import org.wikipedia.WikipediaApp;
import org.wikipedia.descriptions.DescriptionEditActivity.Action;
import org.wikipedia.json.GsonUtil;

import java.util.ArrayList;
import java.util.List;

import static org.wikipedia.Constants.InvokeSource.SUGGESTED_EDITS;
import static org.wikipedia.descriptions.DescriptionEditActivity.Action.ADD_CAPTION;
import static org.wikipedia.descriptions.DescriptionEditActivity.Action.ADD_DESCRIPTION;
import static org.wikipedia.descriptions.DescriptionEditActivity.Action.TRANSLATE_CAPTION;
import static org.wikipedia.descriptions.DescriptionEditActivity.Action.TRANSLATE_DESCRIPTION;

public final class SuggestedEditsFunnel extends TimedFunnel {
    private static SuggestedEditsFunnel INSTANCE;

    private static final String SCHEMA_NAME = "MobileWikiAppSuggestedEdits";
    private static final int REV_ID = 18949003;

    private static final String SUGGESTED_EDITS_UI_VERSION = "1.0";
    private static final String SUGGESTED_EDITS_API_VERSION = "1.0";

    public static final String SUGGESTED_EDITS_ADD_COMMENT = "#suggestededit-add " + SUGGESTED_EDITS_UI_VERSION;
    public static final String SUGGESTED_EDITS_TRANSLATE_COMMENT = "#suggestededit-translate " + SUGGESTED_EDITS_UI_VERSION;

    private InvokeSource invokeSource;
    private String parentSessionToken;
    private int helpOpenedCount = 0;
    private int contributionsOpenedCount = 0;
    private SuggestedEditStatsCollection statsCollection = new SuggestedEditStatsCollection();
    private List<String> uniqueTitles = new ArrayList<>();

    private SuggestedEditsFunnel(WikipediaApp app, InvokeSource invokeSource) {
        super(app, SCHEMA_NAME, REV_ID, Funnel.SAMPLE_LOG_ALL);
        this.invokeSource = invokeSource;
        this.parentSessionToken = app.getSessionFunnel().getSessionToken();
    }

    public static SuggestedEditsFunnel get(InvokeSource invokeSource) {
        if (INSTANCE == null) {
            INSTANCE = new SuggestedEditsFunnel(WikipediaApp.getInstance(), invokeSource);
        } else if (INSTANCE.invokeSource != invokeSource) {
            INSTANCE.log();
            INSTANCE = new SuggestedEditsFunnel(WikipediaApp.getInstance(), invokeSource);
        }
        return INSTANCE;
    }

    public static SuggestedEditsFunnel get() {
        if (INSTANCE != null && INSTANCE.invokeSource != SUGGESTED_EDITS) {
            return INSTANCE;
        }
        return get(SUGGESTED_EDITS);
    }

    public static void reset() {
        INSTANCE = null;
    }

    @Override protected void preprocessSessionToken(@NonNull JSONObject eventData) {
        preprocessData(eventData, "session_token", parentSessionToken);
    }

    public void impression(Action action) {
        if (action == ADD_DESCRIPTION) {
            statsCollection.addDescriptionStats.impressions++;
        } else if (action == TRANSLATE_DESCRIPTION) {
            statsCollection.translateDescriptionStats.impressions++;
        } else if (action == ADD_CAPTION) {
            statsCollection.addCaptionStats.impressions++;
        } else if (action == TRANSLATE_CAPTION) {
            statsCollection.translateCaptionStats.impressions++;
        }
    }


    public void click(String title, Action action) {
        SuggestedEditStats stats;
        if (action == ADD_DESCRIPTION) {
            stats = statsCollection.addDescriptionStats;
        } else if (action == TRANSLATE_DESCRIPTION) {
            stats = statsCollection.translateDescriptionStats;
        } else if (action == ADD_CAPTION) {
            stats = statsCollection.addCaptionStats;
        } else if (action == TRANSLATE_CAPTION) {
            stats = statsCollection.translateCaptionStats;
        } else {
            return;
        }
        stats.clicks++;
        if (!uniqueTitles.contains(title)) {
            uniqueTitles.add(title);
            final int maxItems = 100;
            if (uniqueTitles.size() > maxItems) {
                uniqueTitles.remove(0);
            }
            stats.suggestionsClicked++;
        }
    }

    public void cancel(Action action) {
        if (action == ADD_DESCRIPTION) {
            statsCollection.addDescriptionStats.cancels++;
        } else if (action == TRANSLATE_DESCRIPTION) {
            statsCollection.translateDescriptionStats.cancels++;
        } else if (action == ADD_CAPTION) {
            statsCollection.addCaptionStats.cancels++;
        } else if (action == TRANSLATE_CAPTION) {
            statsCollection.translateCaptionStats.cancels++;
        }
    }

    public void success(Action action) {
        if (action == ADD_DESCRIPTION) {
            statsCollection.addDescriptionStats.successes++;
        } else if (action == TRANSLATE_DESCRIPTION) {
            statsCollection.translateDescriptionStats.successes++;
        } else if (action == ADD_CAPTION) {
            statsCollection.addCaptionStats.successes++;
        } else if (action == TRANSLATE_CAPTION) {
            statsCollection.translateCaptionStats.successes++;
        }
    }

    public void failure(Action action) {
        if (action == ADD_DESCRIPTION) {
            statsCollection.addDescriptionStats.failures++;
        } else if (action == TRANSLATE_DESCRIPTION) {
            statsCollection.translateDescriptionStats.failures++;
        } else if (action == ADD_CAPTION) {
            statsCollection.addCaptionStats.failures++;
        } else if (action == TRANSLATE_CAPTION) {
            statsCollection.translateCaptionStats.failures++;
        }
    }

    public void helpOpened() {
        helpOpenedCount++;
    }

    public void contributionsOpened() {
        contributionsOpenedCount++;
    }

    public void log() {
        log(
                "edit_tasks", GsonUtil.getDefaultGson().toJson(statsCollection),
                "help_opened", helpOpenedCount,
                "scorecard_opened", contributionsOpenedCount,
                "source", (invokeSource.getName())
        );
    }

    private static class SuggestedEditStatsCollection {
        @SerializedName("add-description") private SuggestedEditStats addDescriptionStats = new SuggestedEditStats();
        @SerializedName("translate-description") private SuggestedEditStats translateDescriptionStats = new SuggestedEditStats();
        @SerializedName("add-caption") private SuggestedEditStats addCaptionStats = new SuggestedEditStats();
        @SerializedName("translate-caption") private SuggestedEditStats translateCaptionStats = new SuggestedEditStats();
    }

    @SuppressWarnings("unused")
    private static class SuggestedEditStats {
        private int impressions;
        private int clicks;
        @SerializedName("suggestions_clicked") private int suggestionsClicked;
        private int cancels;
        private int successes;
        private int failures;
    }
}
