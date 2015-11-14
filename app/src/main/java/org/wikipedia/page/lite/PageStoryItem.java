package org.wikipedia.page.lite;

import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.wikipedia.page.PageFragmentBase;

public abstract class PageStoryItem {

    protected PageFragmentBase parentFragment;

    public PageStoryItem(PageFragmentBase parentFragment) {
        this.parentFragment = parentFragment;
    }

    public abstract View renderIntoView(LayoutInflater inflater, ViewGroup container, LinkMovementMethod lmm);
}