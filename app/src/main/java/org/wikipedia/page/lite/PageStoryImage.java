package org.wikipedia.page.lite;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;
import org.w3c.dom.Text;
import org.wikipedia.R;
import org.wikipedia.WikipediaApp;
import org.wikipedia.page.PageFragmentBase;
import org.wikipedia.page.PageFragmentLite;
import org.wikipedia.page.PageTitle;
import org.wikipedia.page.gallery.GalleryItem;
import org.wikipedia.page.gallery.GalleryItemFetchTask;
import org.wikipedia.server.restbase.RbPageLead;
import org.wikipedia.util.FeedbackUtil;
import org.wikipedia.util.StringUtil;

import java.util.Map;

import uk.co.senab.photoview.PhotoViewAttacher;

public class PageStoryImage extends PageStoryItem {

    private final String thumbUrl;
    private final String fileName;
    private final String caption;
    private final RbPageLead.Media media;
    private PhotoViewAttacher attacher;

    public PageStoryImage(PageFragmentBase parent, String fileName, String thumbUrl, String caption,
                          RbPageLead.Media media) {
        super(parent);
        this.thumbUrl = StringUtil.emptyIfNull(thumbUrl);
        this.fileName = StringUtil.emptyIfNull(fileName);
        this.caption = StringUtil.emptyIfNull(caption);
        this.media = media;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((PageFragmentLite) parentFragment).onImageClicked(fileName);
        }
    };

    @Override
    public View renderIntoView(final LayoutInflater inflater, ViewGroup container, LinkMovementMethod lmm) {
        View rootView = inflater.inflate(R.layout.item_page_story_image, container, false);
        final ImageView image = (ImageView) rootView.findViewById(R.id.page_image);
        TextView imageCaption = (TextView) rootView.findViewById(R.id.page_image_caption);

        imageCaption.setText(Html.fromHtml(caption));
        imageCaption.setMovementMethod(lmm);

        if (attacher != null) {
            attacher.cleanup();
        }
        attacher = new PhotoViewAttacher(image);
        attacher.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float v, float v2) {
                ((PageFragmentLite) parentFragment).onImageClicked(fileName);
            }
        });

        image.setOnClickListener(onClickListener);

        boolean mediaFound = false;
        if (media != null) {
            for (RbPageLead.MediaItem item : media.getItems()) {
                if (("/wiki/" + item.getTitle()).equals(fileName.replace("_", " "))) {
                    mediaFound = true;
                    final float aspect = item.getHeight() > 0 ? (float) item.getWidth() / (float) item.getHeight() : 1;
                    PageTitle imageTitle = parentFragment.getTitleOriginal().getSite().titleForInternalLink(fileName);
                    new GalleryItemFetchTask(WikipediaApp.getInstance().getPrimarySiteApi(), imageTitle.getSite(), imageTitle, false) {
                        @Override
                        public void onFinish(Map<PageTitle, GalleryItem> result) {
                            if (result.size() > 0) {
                                GalleryItem galleryItem = (GalleryItem) result.values().toArray()[0];
                                if (!TextUtils.isEmpty(galleryItem.getThumbUrl())) {
                                    loadImage(inflater.getContext(), image, galleryItem.getThumbUrl(), aspect);
                                }
                            }
                        }

                        @Override
                        public void onCatch(Throwable caught) {
                            Log.e("Wikipedia", "caught " + caught.getMessage());
                            caught.printStackTrace();
                        }
                    }.execute();

                }
            }
        }

        if (!mediaFound) {
            Log.d("Wikipedia", ">>>>>>>>> Image not found: " + fileName);
            loadImage(inflater.getContext(), image, thumbUrl, 1f);
        }
        return rootView;
    }

    private void loadImage(Context context, final ImageView targetView, String url, final float aspect) {
        if (WikipediaApp.getInstance().isImageDownloadEnabled() && !TextUtils.isEmpty(url)) {
            Picasso.with(context)
                    .load(url)
                    .placeholder(R.drawable.lead_default)
                    .error(R.drawable.lead_default)
                    .into(targetView, new Callback() {
                        @Override
                        public void onSuccess() {
                            attacher.update();
                            attacher.setZoomable(true);
                            scaleImageToWindow(targetView, aspect);
                        }

                        @Override
                        public void onError() {
                        }
                    });
        } else {
            Picasso.with(context)
                    .load(R.drawable.lead_default)
                    .into(targetView, new Callback() {
                        @Override
                        public void onSuccess() {
                            attacher.update();
                            attacher.setZoomable(false);
                            scaleImageToWindow(targetView, aspect);
                        }

                        @Override
                        public void onError() {
                        }
                    });
        }
    }

    private void scaleImageToWindow(ImageView view, float aspect) {
        if (view.getWidth() == 0 || view.getHeight() == 0) {
            return;
        }
        float windowAspect = (float) view.getWidth()
                / (float) view.getHeight();
        if (windowAspect > aspect) {
            attacher.setScale(windowAspect / aspect);
        } else {
            attacher.setScale(aspect / windowAspect);
        }
    }
}