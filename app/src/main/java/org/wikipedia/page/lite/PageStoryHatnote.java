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

public class PageStoryHatnote extends PageStoryItem {
    private final String text;

    public PageStoryHatnote(PageFragmentBase parent, String text) {
        super(parent);
        this.text = StringUtil.emptyIfNull(text);
    }

    @Override
    public View renderIntoView(LayoutInflater inflater, ViewGroup container, LinkMovementMethod lmm) {
        View rootView = inflater.inflate(R.layout.item_page_story_hatnote, container, false);
        TextView headingText = (TextView) rootView.findViewById(R.id.page_hatnote_text);
        headingText.setText(Html.fromHtml(text));
        headingText.setMovementMethod(lmm);
        return rootView;
    }
}