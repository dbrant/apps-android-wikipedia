package org.wikipedia.server;

import android.support.annotation.Nullable;

import org.wikipedia.page.Section;

import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

public interface PageLite {

    boolean hasError();

    ServiceError getError();

    void logError(String message);

    /** Note: before using this check that #hasError is false */
    List<Section> getSectionsLite();

    String getLeadSectionContent();

    @Nullable
    String getTitlePronunciationUrl();

    /** So we can have polymorphic Retrofit Callbacks */
    interface Callback {
        void success(PageLite pageLite, Response response);

        void failure(RetrofitError error);
    }
}
