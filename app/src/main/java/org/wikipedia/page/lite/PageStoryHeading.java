package org.wikipedia.page.lite;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.wikipedia.R;
import org.wikipedia.page.PageFragmentBase;
import org.wikipedia.util.StringUtil;

public class PageStoryHeading extends PageStoryItem {
    private final String heading;

    public PageStoryHeading(PageFragmentBase parent, String heading) {
        super(parent);
        this.heading = StringUtil.emptyIfNull(heading);
    }

    @Override
    public View renderIntoView(LayoutInflater inflater, ViewGroup container, LinkMovementMethod lmm) {
        View rootView = inflater.inflate(R.layout.item_page_story_heading, container, false);
        TextView headingText = (TextView) rootView.findViewById(R.id.page_heading_text);
        headingText.setText(Html.fromHtml(heading));
        headingText.setMovementMethod(lmm);
        return rootView;
    }
}