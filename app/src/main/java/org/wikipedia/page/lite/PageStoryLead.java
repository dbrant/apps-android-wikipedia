package org.wikipedia.page.lite;

import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.wikipedia.R;
import org.wikipedia.WikipediaApp;
import org.wikipedia.page.PageFragmentBase;
import org.wikipedia.page.PageFragmentLite;
import org.wikipedia.util.GradientUtil;
import org.wikipedia.util.StringUtil;
import org.wikipedia.views.ViewUtil;

public class PageStoryLead extends PageStoryItem {
    private final String leadImageName;
    private final String leadImageUrl;
    private final String title;

    public PageStoryLead(PageFragmentBase parent, String leadImageName, String leadImageUrl, String title) {
        super(parent);
        this.leadImageName = StringUtil.emptyIfNull(leadImageName);
        this.leadImageUrl = WikipediaApp.getInstance().getNetworkProtocol() + ":" + StringUtil.emptyIfNull(leadImageUrl);
        this.title = StringUtil.emptyIfNull(title);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((PageFragmentLite) parentFragment).onImageClicked(leadImageName);
        }
    };

    @Override
    public View renderIntoView(final LayoutInflater inflater, ViewGroup container, LinkMovementMethod lmm) {
        View rootView = inflater.inflate(R.layout.item_page_story_lead, container, false);
        ImageView image = (ImageView) rootView.findViewById(R.id.page_image);
        TextView titleText = (TextView) rootView.findViewById(R.id.page_title_text);
        View gradientView = rootView.findViewById(R.id.page_title_gradient);
        ViewUtil.setBackgroundDrawable(gradientView, GradientUtil.getCubicGradient(
                inflater.getContext().getResources().getColor(R.color.lead_gradient_start), Gravity.BOTTOM));

        titleText.setText(Html.fromHtml(title));
        image.setOnClickListener(onClickListener);

        if (WikipediaApp.getInstance().isImageDownloadEnabled() && !TextUtils.isEmpty(leadImageUrl)) {
            Picasso.with(inflater.getContext())
                    .load(leadImageUrl)
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