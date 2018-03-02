package org.wikipedia.views;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;

import org.wikipedia.WikipediaApp;
import org.wikipedia.dataclient.mwapi.MwQueryResponse;
import org.wikipedia.gallery.GalleryItem;
import org.wikipedia.gallery.GalleryItemClient;
import org.wikipedia.page.PageTitle;

import retrofit2.Call;

public class CommonsDraweeView extends FaceAndColorDetectImageView {

    public CommonsDraweeView(Context context) {
        super(context);
        init();
    }

    public CommonsDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CommonsDraweeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void loadImage(String commonsTitle) {
        new GalleryItemClient().request(WikipediaApp.getInstance().getWikiSite(),
                new PageTitle(commonsTitle, WikipediaApp.getInstance().getWikiSite()),
                new GalleryItemClient.Callback() {
                    @Override
                    public void success(@NonNull Call<MwQueryResponse> call, @NonNull GalleryItem result) {
                        loadImage(Uri.parse(result.getThumbUrl()));
                    }

                    @Override
                    public void failure(@NonNull Call<MwQueryResponse> call, @NonNull Throwable caught) {
                        Log.e("Wikipedia", "caught " + caught.getMessage());
                        // error...
                    }
                }, false);
    }

    private void init() {
    }
}
