package org.wikipedia.page.lite;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.wikipedia.R;
import org.wikipedia.page.PageFragmentBase;
import org.wikipedia.page.linkpreview.LinkPreviewContents;
import org.wikipedia.util.StringUtil;

public class PageStoryParagraph extends PageStoryItem {
    private String text;

    public PageStoryParagraph(PageFragmentBase parent, String text, int sectionIndex) {
        super(parent);
        this.text = StringUtil.emptyIfNull(text);
        if (sectionIndex == 0) {
            this.text = LinkPreviewContents.removeParens(text);
        }
    }

    @Override
    public View renderIntoView(LayoutInflater inflater, ViewGroup container, LinkMovementMethod lmm) {
        View rootView = inflater.inflate(R.layout.item_page_story_paragraph, container, false);
        TextView paragraphText = (TextView) rootView.findViewById(R.id.page_paragraph_text);
        paragraphText.setText(Html.fromHtml(text));
        paragraphText.setMovementMethod(lmm);
        paragraphText.setTextIsSelectable(true);
        return rootView;
    }
}