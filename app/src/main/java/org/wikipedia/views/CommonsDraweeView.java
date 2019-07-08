package org.wikipedia.views;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import org.wikipedia.dataclient.Service;
import org.wikipedia.dataclient.ServiceFactory;
import org.wikipedia.dataclient.WikiSite;
import org.wikipedia.util.log.L;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CommonsDraweeView extends FaceAndColorDetectImageView {
    @Nullable private Disposable disposable;

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
        cancel();
        disposable = ServiceFactory.get(new WikiSite(Service.COMMONS_URL))
                .getImageExtMetadata(commonsTitle)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> loadImage(Uri.parse(response.query().firstPage().imageInfo().getThumbUrl())),
                        L::e);
    }

    private void cancel() {
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
    }

    @Override public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        cancel();
    }

    private void init() {
    }
}
