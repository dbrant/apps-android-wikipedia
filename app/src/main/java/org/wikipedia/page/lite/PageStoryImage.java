package org.wikipedia.page.lite;

import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;
import org.wikipedia.R;
import org.wikipedia.WikipediaApp;
import org.wikipedia.page.PageFragmentBase;
import org.wikipedia.page.PageFragmentLite;
import org.wikipedia.util.StringUtil;

public class PageStoryImage extends PageStoryItem {

    private final String thumbUrl;
    private final String fileName;
    private final String caption;

    public PageStoryImage(PageFragmentBase parent, String fileName, String thumbUrl, String caption) {
        super(parent);
        this.thumbUrl = StringUtil.emptyIfNull(thumbUrl);
        this.fileName = StringUtil.emptyIfNull(fileName);
        this.caption = StringUtil.emptyIfNull(caption);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((PageFragmentLite) parentFragment).onImageClicked((String) v.getTag());
        }
    };

    @Override
    public View renderIntoView(LayoutInflater inflater, ViewGroup container, LinkMovementMethod lmm) {
        View rootView = inflater.inflate(R.layout.item_page_story_image, container, false);
        ImageView image = (ImageView) rootView.findViewById(R.id.page_image);
        TextView imageCaption = (TextView) rootView.findViewById(R.id.page_image_caption);

        imageCaption.setText(Html.fromHtml(caption));
        imageCaption.setMovementMethod(lmm);
        image.setTag(fileName);
        image.setOnClickListener(onClickListener);

        if (WikipediaApp.getInstance().isImageDownloadEnabled() && !TextUtils.isEmpty(thumbUrl)) {
            Picasso.with(inflater.getContext())
                    .load(WikipediaApp.getInstance().getNetworkProtocol() + ":" + thumbUrl)
                    .placeholder(R.drawable.lead_default)
                    .error(R.drawable.lead_default)
                    .into(image);
        } else {
            Picasso.with(inflater.getContext())
                    .load(R.drawable.lead_default)
                    .into(image);
        }
        return rootView;
    }
}