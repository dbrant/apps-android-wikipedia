package org.wikipedia.page;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;

import org.wikipedia.history.HistoryEntry;
import org.wikipedia.page.snippet.CompatActionMode;
import org.wikipedia.page.tabs.Tab;
import org.wikipedia.views.ObservableWebView;

public abstract class PageFragmentBase extends Fragment {

    public abstract void displayNewPage(PageTitle title, HistoryEntry entry,
                                        PageLoadStrategy.Cache cachePreference,
                                        boolean pushBackStack, int stagedScrollY);

    public abstract void displayNewPage(PageTitle title,
                                        HistoryEntry entry, PageLoadStrategy.Cache cachePreference,
                                        boolean pushBackStack);

    public abstract void openInNewBackgroundTabFromMenu(PageTitle title, HistoryEntry entry);

    public abstract void openInNewForegroundTabFromMenu(PageTitle title, HistoryEntry entry);

    public abstract void commonSectionFetchOnCatch(Throwable error);

    public abstract void setupToC(PageViewModel model, boolean isFirstPage);

    public abstract Tab getCurrentTab();

    public abstract void scrollToSection(String sectionAnchor);

    public abstract LinkHandler getLinkHandler();

    public abstract HistoryEntry getHistoryEntry();

    public abstract void refreshPage(boolean saveOnComplete);

    public abstract PageTitle getTitle();

    public abstract PageTitle getTitleOriginal();

    public abstract Page getPage();

    public abstract void onPageLoadComplete();

    public abstract void readUrlMappings();

    public abstract void invalidateTabs();

    public abstract boolean closeFindInPage();

    public abstract void toggleToC(int action);

    public ObservableWebView getWebView() { return null; }

    public abstract void updateFontSize();

    public abstract void onActionModeShown(CompatActionMode mode);

    public Bitmap getLeadImageBitmap() { return null; }

    public float getLeadImageFocusY() { return 0.0f; }

}
