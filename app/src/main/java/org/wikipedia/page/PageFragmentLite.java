package org.wikipedia.page;

import org.wikipedia.BackPressedHandler;
import org.wikipedia.NightModeHandler;
import org.wikipedia.R;
import org.wikipedia.Site;
import org.wikipedia.WikipediaApp;
import org.wikipedia.analytics.ConnectionIssueFunnel;
import org.wikipedia.analytics.FindInPageFunnel;
import org.wikipedia.analytics.GalleryFunnel;
import org.wikipedia.analytics.LinkPreviewFunnel;
import org.wikipedia.analytics.PageScrollFunnel;
import org.wikipedia.analytics.SavedPagesFunnel;
import org.wikipedia.analytics.TabFunnel;
import org.wikipedia.bridge.CommunicationBridge;
import org.wikipedia.bridge.StyleBundle;
import org.wikipedia.editing.EditHandler;
import org.wikipedia.history.HistoryEntry;
import org.wikipedia.interlanguage.LangLinksActivity;
import org.wikipedia.page.gallery.GalleryActivity;
import org.wikipedia.page.leadimages.LeadImagesHandler;
import org.wikipedia.page.linkpreview.LinkPreviewContents;
import org.wikipedia.page.lite.LinkHandlerLite;
import org.wikipedia.page.lite.PageStoryGeo;
import org.wikipedia.page.lite.PageStoryHatnote;
import org.wikipedia.page.lite.PageStoryHeading;
import org.wikipedia.page.lite.PageStoryImage;
import org.wikipedia.page.lite.PageStoryItem;
import org.wikipedia.page.lite.PageStoryParagraph;
import org.wikipedia.page.snippet.CompatActionMode;
import org.wikipedia.page.snippet.ShareHandler;
import org.wikipedia.page.tabs.Tab;
import org.wikipedia.page.tabs.TabsProvider;
import org.wikipedia.savedpages.ImageUrlMap;
import org.wikipedia.savedpages.LoadSavedPageUrlMapTask;
import org.wikipedia.savedpages.SavePageTask;
import org.wikipedia.savedpages.SavedPageCheckTask;
import org.wikipedia.search.SearchBarHideHandler;
import org.wikipedia.server.PageCombo;
import org.wikipedia.server.PageLead;
import org.wikipedia.server.PageServiceFactory;
import org.wikipedia.server.restbase.RbPageCombo;
import org.wikipedia.server.restbase.RbPageLead;
import org.wikipedia.server.restbase.RbPageService;
import org.wikipedia.settings.Prefs;
import org.wikipedia.tooltip.ToolTipUtil;
import org.wikipedia.util.FeedbackUtil;
import org.wikipedia.util.ShareUtil;
import org.wikipedia.util.ThrowableUtil;
import org.wikipedia.util.log.L;
import org.wikipedia.views.ArticleHeaderView;
import org.wikipedia.views.ObservableWebView;
import org.wikipedia.views.SwipeRefreshLayoutWithScroll;
import org.wikipedia.views.WikiDrawerLayout;

import org.json.JSONException;
import org.json.JSONObject;
import org.wikipedia.views.WikiErrorView;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.appenguin.onboarding.ToolTip;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLException;

import retrofit.RetrofitError;
import retrofit.client.Response;

import static butterknife.ButterKnife.findById;
import static org.wikipedia.util.DeviceUtil.hideSoftKeyboard;
import static org.wikipedia.util.DimenUtil.getContentTopOffsetPx;
import static org.wikipedia.util.DimenUtil.getContentTopOffset;
import static org.wikipedia.util.ResourceUtil.getThemedAttributeId;
import static org.wikipedia.util.UriUtil.decodeURL;
import static org.wikipedia.util.UriUtil.visitInExternalBrowser;

public class PageFragmentLite extends PageFragmentBase implements BackPressedHandler {
    public static final int TOC_ACTION_SHOW = 0;
    public static final int TOC_ACTION_HIDE = 1;
    public static final int TOC_ACTION_TOGGLE = 2;

    private boolean pageSaved;
    private boolean pageRefreshed;
    private boolean savedPageCheckComplete;
    private boolean errorState = false;

    private static final int TOC_BUTTON_HIDE_DELAY = 2000;
    private static final int REFRESH_SPINNER_ADDITIONAL_OFFSET = (int) (16 * WikipediaApp.getInstance().getScreenDensity());

    private PageViewModel model;

    /**
     * List of tabs, each of which contains a backstack of page titles.
     * Since the list consists of Parcelable objects, it can be saved and restored from the
     * savedInstanceState of the fragment.
     */
    @NonNull
    private final List<Tab> tabList = new ArrayList<>();

    @NonNull
    private TabFunnel tabFunnel = new TabFunnel();

    @Nullable
    private PageScrollFunnel pageScrollFunnel;

    /**
     * Whether to save the full page content as soon as it's loaded.
     * Used in the following cases:
     * - Stored page content is corrupted
     * - Page bookmarks are imported from the old app.
     * In the above cases, loading of the saved page will "fail", and will
     * automatically bounce to the online version of the page. Once the online page
     * loads successfully, the content will be saved, thereby reconstructing the
     * stored version of the page.
     */
    private boolean saveOnComplete = false;

    private SearchBarHideHandler searchBarHideHandler;

    //private SwipeRefreshLayout refreshView;

    private WikiErrorView errorView;

    private ListView storyList;
    private PageStoryAdapter storyListAdapter;

    //private WikiDrawerLayout tocDrawer;

    private FloatingActionButton tocButton;

    private LinkHandlerLite linkHandler;
    private ActionMode findInPageActionMode;
    //private ShareHandler shareHandler;
    private TabsProvider tabsProvider;

    private WikipediaApp app;

    private SavedPagesFunnel savedPagesFunnel;
    private ConnectionIssueFunnel connectionIssueFunnel;

    @NonNull
    private final SwipeRefreshLayout.OnRefreshListener pageRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            // don't refresh if it's still loading...


            //TODO
            //if (pageLoadStrategy.isLoading() || !savedPageCheckComplete || errorState) {
            if (!savedPageCheckComplete || errorState) {



                //refreshView.setRefreshing(false);
                return;
            }
            // if it's a saved page, then refresh it and re-save. Otherwise, refresh the page normally
            refreshPage(isPageSaved());
        }
    };

    @NonNull
    private final View.OnClickListener tocButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            hideSoftKeyboard(getActivity());
            setToCButtonFadedIn(true);
            toggleToC(TOC_ACTION_TOGGLE);
        }
    };

    @Nullable
    private PageLoadCallbacks pageLoadCallbacks;

    public PageTitle getTitle() {
        return model.getTitle();
    }

    public PageTitle getTitleOriginal() {
        return model.getTitleOriginal();
    }

    @Nullable public Page getPage() {
        return model.getPage();
    }

    public HistoryEntry getHistoryEntry() {
        return model.getCurEntry();
    }

    public void setSavedPageCheckComplete(boolean complete) {
        savedPageCheckComplete = complete;
        if (!isAdded()) {
            return;
        }
        getActivity().supportInvalidateOptionsMenu();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (WikipediaApp) getActivity().getApplicationContext();
        model = new PageViewModel();
        initTabs();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page_lite, container, false);

        //tocDrawer = (WikiDrawerLayout) rootView.findViewById(R.id.page_toc_drawer);
        //tocDrawer.setDragEdgeWidth(getResources().getDimensionPixelSize(R.dimen.drawer_drag_margin));

        tocButton = (FloatingActionButton) rootView.findViewById(R.id.floating_toc_button);
        tocButton.setOnClickListener(tocButtonOnClickListener);

        //refreshView = (SwipeRefreshLayout) rootView.findViewById(R.id.page_refresh_container);
        //int swipeOffset = getContentTopOffsetPx(getActivity()) + REFRESH_SPINNER_ADDITIONAL_OFFSET;
        //refreshView.setProgressViewOffset(false, -swipeOffset, swipeOffset);
        // if we want to give it a custom color:
        //refreshView.setProgressBackgroundColor(R.color.swipe_refresh_circle);
        //refreshView.setOnRefreshListener(pageRefreshListener);

        errorView = (WikiErrorView)rootView.findViewById(R.id.page_error);

        storyList = (ListView) rootView.findViewById(R.id.page_story_list);
        storyList.setPadding(0, getContentTopOffsetPx(getActivity()), 0, 0);
        storyListAdapter = new PageStoryAdapter(inflater);
        storyList.setAdapter(storyListAdapter);

        return rootView;
    }

    public void onDestroyView() {
        //shareHandler.onDestroy();
        super.onDestroyView();
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        connectionIssueFunnel = new ConnectionIssueFunnel(app);

        updateFontSize();

        linkHandler = new LinkHandlerLite(getActivity()) {
            @Override
            public void onPageLinkClicked(String anchor) {
            }

            @Override
            public void onInternalLinkClicked(PageTitle title) {
                handleInternalLink(title);
            }

            @Override
            public Site getSite() {
                return model.getTitle().getSite();
            }
        };

        errorView.setRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorView.setVisibility(View.GONE);
                errorState = false;
                displayNewPage(model.getTitleOriginal(), model.getCurEntry(), PageLoadStrategy.Cache.FALLBACK, false);
            }
        });


        //tocHandler = new ToCHandler(getPageActivity(), tocDrawer, bridge);

        searchBarHideHandler = getPageActivity().getSearchBarHideHandler();

        //searchBarHideHandler.setScrollView(webView);

        //shareHandler = new ShareHandler(getPageActivity(), bridge);

        tabsProvider = new TabsProvider(getPageActivity(), tabList);
        tabsProvider.setTabsProviderListener(tabsProviderListener);

        PageLongPressHandler.WebViewContextMenuListener contextMenuListener = new LongPressHandler(getPageActivity());

        //new PageLongPressHandler(getActivity(), webView, HistoryEntry.SOURCE_INTERNAL_LINK, contextMenuListener);

    }

    private void handleInternalLink(PageTitle title) {
        if (!isResumed()) {
            return;
        }
        // if it's a Special page, launch it in an external browser, since mobileview
        // doesn't support the Special namespace.
        // TODO: remove when Special pages are properly returned by the server
        if (title.isSpecial()) {
            visitInExternalBrowser(getActivity(), Uri.parse(title.getMobileUri()));
            return;
        }
        if (!TextUtils.isEmpty(title.getNamespace()) || !app.isLinkPreviewEnabled()) {
            HistoryEntry historyEntry = new HistoryEntry(title, HistoryEntry.SOURCE_INTERNAL_LINK);
            getPageActivity().displayNewPage(title, historyEntry);
            new LinkPreviewFunnel(app).logNavigate();
        } else {
            getPageActivity().showLinkPreview(title, HistoryEntry.SOURCE_INTERNAL_LINK);
        }
    }

    private TabsProvider.TabsProviderListener tabsProviderListener = new TabsProvider.TabsProviderListener() {
        @Override
        public void onEnterTabView() {
            tabFunnel = new TabFunnel();
            tabFunnel.logEnterList(tabList.size());
        }

        @Override
        public void onCancelTabView() {
            tabsProvider.exitTabMode();
            tabFunnel.logCancel(tabList.size());
        }

        @Override
        public void onTabSelected(int position) {
            // move the selected tab to the bottom of the list, and navigate to it!
            // (but only if it's a different tab than the one currently in view!
            if (position != tabList.size() - 1) {
                Tab tab = tabList.remove(position);
                tabList.add(tab);

                //TODO
                //pageLoadStrategy.updateCurrentBackStackItem();
                //pageLoadStrategy.setBackStack(tab.getBackStack());
                //pageLoadStrategy.loadPageFromBackStack();

            }
            tabsProvider.exitTabMode();
            tabFunnel.logSelect(tabList.size(), position);
        }

        @Override
        public void onNewTabRequested() {
            // just load the main page into a new tab...
            getPageActivity().displayMainPageInForegroundTab();
            tabFunnel.logCreateNew(tabList.size());
        }

        @Override
        public void onCloseTabRequested(int position) {
            if (!app.isDevRelease() && (position < 0 || position >= tabList.size())) {
                // According to T109998, the position may possibly be out-of-bounds, but we can't
                // reproduce it. We'll handle this case, but only for non-dev builds, so that we
                // can investigate the issue further if we happen upon it ourselves.
                return;
            }
            tabList.remove(position);
            tabFunnel.logClose(tabList.size(), position);
            if (position < tabList.size()) {
                // if it's not the topmost tab, then just delete it and update the tab list...
                tabsProvider.invalidate();
            } else if (tabList.size() > 0) {
                tabsProvider.invalidate();
                // but if it's the topmost tab, then load the topmost page in the next tab.


                //TODO
                //pageLoadStrategy.setBackStack(getCurrentTab().getBackStack());
                //pageLoadStrategy.loadPageFromBackStack();


            } else {
                tabFunnel.logCancel(tabList.size());
                // and if the last tab was closed, then finish the activity!
                getActivity().finish();
            }
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        Prefs.setTabs(tabList);
        closePageScrollFunnel();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");
        initPageScrollFunnel();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);


        //TODO
        //sendDecorOffsetMessage();
        /*
        // if the screen orientation changes, then re-layout the lead image container,
        // but only if we've finished fetching the page.
        if (!pageLoadStrategy.isLoading()) {
            leadImagesHandler.beginLayout(new LeadImagesHandler.OnLeadImageLayoutListener() {
                @Override
                public void onLayoutComplete(int sequence) {
                    // (We don't care about the sequence number here, since it doesn't affect
                    // page loading)
                    // When it's finished laying out, make sure the toolbar is shown appropriately.
                    searchBarHideHandler.setFadeEnabled(leadImagesHandler.isLeadImageEnabled());
                }
            }, 0);
        }
        */


        tabsProvider.onConfigurationChanged();
    }

    public Tab getCurrentTab() {
        return tabList.get(tabList.size() - 1);
    }

    public void invalidateTabs() {
        tabsProvider.invalidate();
    }

    public void openInNewBackgroundTabFromMenu(PageTitle title, HistoryEntry entry) {
        openInNewTabFromMenu(title, entry, getBackgroundTabPosition());
    }

    public void openInNewForegroundTabFromMenu(PageTitle title, HistoryEntry entry) {
        openInNewTabFromMenu(title, entry, getForegroundTabPosition());
        displayNewPage(title, entry, PageLoadStrategy.Cache.FALLBACK, false);
    }

    public void openInNewTabFromMenu(PageTitle title,
                                     HistoryEntry entry,
                                     int position) {
        openInNewTab(title, entry, position);
        tabFunnel.logOpenInNew(tabList.size());
    }

    public void displayNewPage(PageTitle title, HistoryEntry entry, PageLoadStrategy.Cache cachePreference,
                               boolean pushBackStack) {
        displayNewPage(title, entry, cachePreference, pushBackStack, 0);
    }

    public void displayNewPage(PageTitle title, HistoryEntry entry, PageLoadStrategy.Cache cachePreference,
                               boolean pushBackStack, int stagedScrollY) {
        displayNewPage(title, entry, cachePreference, pushBackStack, stagedScrollY, false);
    }

    public void displayNewPage(PageTitle title, HistoryEntry entry, PageLoadStrategy.Cache cachePreference,
                               boolean pushBackStack, boolean pageRefreshed) {
        displayNewPage(title, entry, cachePreference, pushBackStack, 0, pageRefreshed);
    }

    /**
     * Load a new page into the WebView in this fragment.
     * This shall be the single point of entry for loading content into the WebView, whether it's
     * loading an entirely new page, refreshing the current page, retrying a failed network
     * request, etc.
     * @param title Title of the new page to load.
     * @param entry HistoryEntry associated with the new page.
     * @param cachePreference Whether to try loading the page from cache or from network.
     * @param pushBackStack Whether to push the new page onto the backstack.
     */
    public void displayNewPage(PageTitle title, HistoryEntry entry, PageLoadStrategy.Cache cachePreference,
                               boolean pushBackStack, int stagedScrollY, boolean pageRefreshed) {


        // disable sliding of the ToC while sections are loading
        //TODO
        //tocHandler.setEnabled(false);


        setToCButtonFadedIn(true);

        errorState = false;
        errorView.setVisibility(View.GONE);

        model.setTitle(title);
        model.setTitleOriginal(title);
        model.setCurEntry(entry);
        savedPagesFunnel = app.getFunnelManager().getSavedPagesFunnel(title.getSite());

        getPageActivity().updateProgressBar(true, true, 0);

        this.pageRefreshed = pageRefreshed;
        if (!pageRefreshed) {
            savedPageCheckComplete = false;
            checkIfPageIsSaved();
        }

        closePageScrollFunnel();




        (new RbPageService(model.getTitle().getSite())).pageCombo(
                model.getTitle().getPrefixedText(),
                !app.isImageDownloadEnabled(),
                rbComboOnLoadCallback);
        //TODO
        //pageLoadStrategy.onDisplayNewPage(pushBackStack, cachePreference, stagedScrollY);


    }












    private PageCombo.Callback rbComboOnLoadCallback = new PageCombo.Callback() {
        @Override
        public void success(PageCombo pageCombo, Response response) {
            if (!isAdded()) {
                return;
            }
            L.v(response.getUrl());
            getPageActivity().updateProgressBar(false, true, 0);

            errorView.setVisibility(View.GONE);
            storyList.setVisibility(View.VISIBLE);


            RbPageCombo combo = (RbPageCombo) pageCombo;


            List<PageStoryItem> storyItems = new ArrayList<>();

            storyItems.add(new PageStoryHeading(PageFragmentLite.this,
                    Html.fromHtml(model.getTitle().getDisplayText()).toString()));

            if (combo.getGeo() != null) {
                storyItems.add(new PageStoryGeo(PageFragmentLite.this, combo.getGeo().getLatitude(), combo.getGeo().getLongitude()));
            }

            for (Section section : combo.getSections()) {
                if (section.getItems() == null || section.getItems().size() == 0) {
                    continue;
                }
                if (!TextUtils.isEmpty(section.getHeading())) {
                    storyItems.add(new PageStoryHeading(PageFragmentLite.this, section.getHeading()));
                }

                for (Section.Item item : section.getItems()) {
                    if (item.getType().equals("p")) {
                        if (!TextUtils.isEmpty(item.getText())) {
                            storyItems.add(new PageStoryParagraph(PageFragmentLite.this, item.getText(), section.getId()));
                        }
                    }
                    else if (item.getType().equals("hatnote")) {
                        if (!TextUtils.isEmpty(item.getText())) {
                            storyItems.add(new PageStoryHatnote(PageFragmentLite.this, item.getText()));
                        }
                    }
                    else if (item.getType().equals("image")) {
                        storyItems.add(new PageStoryImage(PageFragmentLite.this, item.getName(),
                                item.getSrc(), item.getCaption(), combo.getMedia()));
                    }
                }
            }

            storyListAdapter.setStoryItems(storyItems);
            storyListAdapter.notifyDataSetInvalidated();

            onPageLoadComplete();
        }

        @Override
        public void failure(RetrofitError error) {
            if (!isAdded()) {
                return;
            }
            getPageActivity().updateProgressBar(false, true, 0);

            commonSectionFetchOnCatch(error);
            L.e("Error loading page: " + error);
        }
    };



    private final class PageStoryAdapter extends BaseAdapter {
        private final LayoutInflater inflater;
        @NonNull private List<PageStoryItem> storyItems = new ArrayList<>();

        private PageStoryAdapter(LayoutInflater inflater) {
            this.inflater = inflater;
        }

        public void setStoryItems(List<PageStoryItem> storyItems) {
            this.storyItems = storyItems;
        }

        @Override
        public int getCount() {
            return storyItems.size();
        }

        @Override
        public Object getItem(int position) {
            return storyItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = storyItems.get(position).renderIntoView(inflater, parent, mLinkMovementMethod);
            return convertView;
        }

    }

    private LinkMovementMethod mLinkMovementMethod = new LinkMovementMethodExt(new LinkMovementMethodExt.UrlHandler() {
        @Override
        public void onUrlClick(String url) {
            PageTitle newTitle = model.getTitleOriginal().getSite().titleForInternalLink(url);
            handleInternalLink(newTitle);
        }
    });













    public Bitmap getLeadImageBitmap() {

        //TODO
        //return leadImagesHandler.getLeadImageBitmap();
        return null;

    }

    /**
     * Returns the normalized (0.0 to 1.0) vertical focus position of the lead image.
     * A value of 0.0 represents the top of the image, and 1.0 represents the bottom.
     * @return Normalized vertical focus position.
     */
    public float getLeadImageFocusY() {


        //TODO
        //return leadImagesHandler.getLeadImageFocusY();
        return 0f;

    }

    /**
     * Update the WebView's base font size, based on the specified font size from the app
     * preferences.
     */
    public void updateFontSize() {


        //TODO
        //webView.getSettings().setDefaultFontSize((int) app.getFontSize(getActivity().getWindow()));


    }

    public boolean isPageSaved() {
        return pageSaved;
    }

    public void setPageSaved(boolean saved) {
        pageSaved = saved;
    }

    public void onActionModeShown(CompatActionMode mode) {
        // make sure we have a page loaded, since shareHandler makes references to it.
        if (model.getPage() != null) {


            //TODO
            //shareHandler.onTextSelected(mode);


        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PageActivity.ACTIVITY_REQUEST_EDIT_SECTION
            && resultCode == EditHandler.RESULT_REFRESH_PAGE) {



            //TODO
            //pageLoadStrategy.backFromEditing(data);



            FeedbackUtil.showMessage(getActivity(), R.string.edit_saved_successfully);
            // and reload the page...
            displayNewPage(model.getTitleOriginal(), model.getCurEntry(), PageLoadStrategy.Cache.NONE, false);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (!isAdded() || getPageActivity().isSearching()) {
            return;
        }
        inflater.inflate(R.menu.menu_page_actions, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (!isAdded() || getPageActivity().isSearching()) {
            return;
        }
        MenuItem savePageItem = menu.findItem(R.id.menu_page_save);
        if (savePageItem == null) {
            return;
        }
        if (getTitle() != null) {
            updateSavePageMenuItem(savePageItem);
        }

        MenuItem shareItem = menu.findItem(R.id.menu_page_share);
        MenuItem otherLangItem = menu.findItem(R.id.menu_page_other_languages);
        MenuItem findInPageItem = menu.findItem(R.id.menu_page_find_in_page);
        MenuItem themeChooserItem = menu.findItem(R.id.menu_page_font_and_theme);



        //TODO:
        //if (pageLoadStrategy.isLoading() || errorState) {
        if (errorState) {


            savePageItem.setEnabled(false);
            shareItem.setEnabled(false);
            otherLangItem.setEnabled(false);
            findInPageItem.setEnabled(false);
            themeChooserItem.setEnabled(false);
        } else {
            shareItem.setEnabled(true);
            // Only display "Read in other languages" if the article is in other languages
            otherLangItem.setVisible(model.getPage() != null && model.getPage().getPageProperties().getLanguageCount() != 0);
            otherLangItem.setEnabled(true);
            findInPageItem.setEnabled(true);
            themeChooserItem.setEnabled(true);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.homeAsUp:
                // TODO SEARCH: add up navigation, see also http://developer.android.com/training/implementing-navigation/ancestral.html
                return true;
            case R.id.menu_page_save:
                if (item.getTitle().equals(getString(R.string.menu_refresh_saved_page))) {
                    refreshPage(true);
                } else {
                    savePage();
                    app.getFunnelManager().getSavedPagesFunnel(model.getTitle().getSite()).logSaveNew();
                }
                return true;
            case R.id.menu_page_share:
                ShareUtil.shareText(getActivity(), model.getTitle());
                return true;
            case R.id.menu_page_other_languages:
                Intent langIntent = new Intent();
                langIntent.setClass(getActivity(), LangLinksActivity.class);
                langIntent.setAction(LangLinksActivity.ACTION_LANGLINKS_FOR_TITLE);
                langIntent.putExtra(LangLinksActivity.EXTRA_PAGETITLE, model.getTitle());
                getActivity().startActivityForResult(langIntent,
                                                     PageActivity.ACTIVITY_REQUEST_LANGLINKS);
                return true;
            case R.id.menu_page_find_in_page:
                showFindInPage();
                return true;
            case R.id.menu_page_font_and_theme:
                getPageActivity().showThemeChooser();
                return true;
            case R.id.menu_page_show_tabs:
                tabsProvider.enterTabMode();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDestroyOptionsMenu() {
        super.onDestroyOptionsMenu();
    }

    public void showFindInPage() {
        final PageActivity pageActivity = getPageActivity();
        final FindInPageFunnel funnel = new FindInPageFunnel(app, model.getTitle().getSite(),
                model.getPage().getPageProperties().getPageId());
        final FindInPageActionProvider findInPageActionProvider
                = new FindInPageActionProvider(pageActivity, funnel);

        pageActivity.startSupportActionMode(new ActionMode.Callback() {
            private final String actionModeTag = "actionModeFindInPage";

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                findInPageActionMode = mode;
                MenuItem menuItem = menu.add(R.string.menu_page_find_in_page);
                MenuItemCompat.setActionProvider(menuItem, findInPageActionProvider);
                setToCButtonFadedIn(false);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                mode.setTag(actionModeTag);
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                findInPageActionMode = null;


                //TODO
                //funnel.setPageHeight(webView.getContentHeight());


                funnel.logDone();


                //TODO
                //webView.clearMatches();


                pageActivity.showToolbar();
                setToCButtonFadedIn(true);
                hideSoftKeyboard(pageActivity);
            }
        });
    }

    public boolean closeFindInPage() {
        if (findInPageActionMode != null) {
            findInPageActionMode.finish();
            return true;
        }
        return false;
    }

    /**
     * Scroll to a specific section in the WebView.
     * @param sectionAnchor Anchor link of the section to scroll to.
     */
    public void scrollToSection(String sectionAnchor) {
        if (!isAdded() || tocHandler == null) {
            return;
        }
        tocHandler.scrollToSection(sectionAnchor);
    }

    public void onPageLoadComplete() {


        //TODO
        //editHandler.setPage(model.getPage());


        initPageScrollFunnel();

        if (saveOnComplete) {
            saveOnComplete = false;
            savedPagesFunnel.logUpdate();
            savePage();
        }

        //TODO:
        //checkAndShowSelectTextOnboarding();

        updateNavDrawerSelection();

        if (pageLoadCallbacks != null) {
            pageLoadCallbacks.onLoadComplete();
        }
    }

    public void commonSectionFetchOnCatch(Throwable caught) {
        if (ThrowableUtil.throwableContainsException(caught, SSLException.class)) {
            try {
                if (WikipediaApp.getInstance().incSslFailCount() < 2) {
                    WikipediaApp.getInstance().setSslFallback(true);
                    connectionIssueFunnel.logConnectionIssue("mdot", "commonSectionFetchOnCatch");
                } else {
                    connectionIssueFunnel.logConnectionIssue("desktop", "commonSectionFetchOnCatch");
                }
            } catch (Exception e) {
                // meh
            }
        }

        if (!isAdded()) {
            return;
        }


        // in any case, make sure the TOC drawer is closed
        //TODO
        //tocDrawer.closeDrawers();


        getPageActivity().updateProgressBar(false, true, 0);
        //refreshView.setRefreshing(false);

        if (pageRefreshed) {
            pageRefreshed = false;
            FeedbackUtil.showError(getActivity(), caught);
            return;
        }

        hidePageContent();
        errorView.setError(caught);
        errorView.setVisibility(View.VISIBLE);
        errorState = true;
    }

    public void savePage() {
        if (model.getPage() == null) {
            return;
        }

        FeedbackUtil.showMessage(getActivity(), R.string.snackbar_saving_page);
        new SavePageTask(app, model.getTitle(), model.getPage()) {
            @Override
            public void onFinish(Boolean success) {
                if (!isAdded()) {
                    Log.d("PageFragment", "Detached from activity, no snackbar.");
                    return;
                }
                getPageActivity().showPageSavedMessage(model.getTitle().getDisplayText(), success);
            }
        }.execute();
        // Not technically a refresh but this will prevent needless immediate refreshing
        pageRefreshed = true;
    }

    /**
     * Read URL mappings from the saved page specific file
     */
    public void readUrlMappings() {
        new LoadSavedPageUrlMapTask(model.getTitle()) {
            @Override
            public void onFinish(JSONObject result) {
                // have we been unwittingly detached from our Activity?
                if (!isAdded()) {
                    Log.d("PageFragment", "Detached from activity, so stopping update.");
                    return;
                }



                //TODO
                //ImageUrlMap.replaceImageSources(bridge, result);


            }

            @Override
            public void onCatch(Throwable caught) {

                /*
                If anything bad happens during loading of a saved page, then simply bounce it
                back to the online version of the page, and re-save the page contents locally when it's done.
                 */

                Log.d("LoadSavedPageTask", "Error loading saved page: " + caught.getMessage());
                caught.printStackTrace();

                refreshPage(true);
            }
        }.execute();
    }

    public void refreshPage(boolean saveOnComplete) {
        this.saveOnComplete = saveOnComplete;
        if (saveOnComplete) {
            FeedbackUtil.showMessage(getActivity(), R.string.snackbar_refresh_saved_page);
        }
        model.setCurEntry(new HistoryEntry(model.getTitle(), HistoryEntry.SOURCE_HISTORY));
        displayNewPage(model.getTitle(), model.getCurEntry(), PageLoadStrategy.Cache.NONE, false, true);
    }

    public void updateSavePageMenuItem(MenuItem menuItemSavePage) {
        if (!savedPageCheckComplete) {
            menuItemSavePage.setEnabled(false);
        } else if (pageSaved) {
            if (pageRefreshed) {
                menuItemSavePage.setEnabled(false);
                menuItemSavePage.setTitle(getString(R.string.menu_page_saved));
            } else {
                menuItemSavePage.setEnabled(true);
                menuItemSavePage.setTitle(getString(R.string.menu_refresh_saved_page));
            }
        } else {
            menuItemSavePage.setEnabled(true);
            menuItemSavePage.setTitle(getString(R.string.menu_page_save));
        }
    }

    private ToCHandler tocHandler;
    public void toggleToC(int action) {
        // tocHandler could still be null while the page is loading
        if (tocHandler == null) {
            return;
        }
        switch (action) {
            case TOC_ACTION_SHOW:
                tocHandler.show();
                break;
            case TOC_ACTION_HIDE:
                tocHandler.hide();
                break;
            case TOC_ACTION_TOGGLE:
                if (tocHandler.isVisible()) {
                    tocHandler.hide();
                } else {
                    tocHandler.show();
                }
                break;
            default:
                throw new RuntimeException("Unknown action!");
        }
    }

    public void setupToC(PageViewModel model, boolean isFirstPage) {
        tocHandler.setupToC(model.getPage(), model.getTitle().getSite(), isFirstPage);
        tocHandler.setEnabled(true);
    }

    private void openInNewTab(PageTitle title, HistoryEntry entry, int position) {
        if (shouldCreateNewTab()) {
            // create a new tab
            Tab tab = new Tab();
            // if the requested position is at the top, then make its backstack current
            if (position == getForegroundTabPosition()) {



                //TODO
                //pageLoadStrategy.setBackStack(tab.getBackStack());


            }
            // put this tab in the requested position
            tabList.add(position, tab);
            // add the requested page to its backstack
            tab.getBackStack().add(new PageBackStackItem(title, entry));
        } else {
            getTopMostTab().getBackStack().add(new PageBackStackItem(title, entry));
        }
        // and... that should be it.
        tabsProvider.showAndHideTabs();
    }

    private Tab getTopMostTab() {
        return tabList.get(tabList.size() - 1);
    }

    private boolean shouldCreateNewTab() {
        return !getTopMostTab().getBackStack().isEmpty();
    }

    private int getBackgroundTabPosition() {
        return Math.max(0, getForegroundTabPosition() - 1);
    }

    private int getForegroundTabPosition() {
        return tabList.size();
    }

    public void onImageClicked(String imageUrl) {
        if (imageUrl.startsWith("/wiki/")) {
            PageTitle imageTitle = model.getTitle().getSite().titleForInternalLink(imageUrl);
            GalleryActivity.showGallery(getActivity(), model.getTitleOriginal(),
                    imageTitle, GalleryFunnel.SOURCE_NON_LEAD_IMAGE);
        } else {
            linkHandler.onUrlClick(imageUrl);
        }
    }

    public void onMediaClicked(String imageWikiFileName) {
        GalleryActivity.showGallery(getActivity(), model.getTitleOriginal(),
                new PageTitle(imageWikiFileName, model.getTitle().getSite()), GalleryFunnel.SOURCE_NON_LEAD_IMAGE);
    }

    private void setToCButtonFadedIn(boolean shouldFadeIn) {
        tocButton.removeCallbacks(hideToCButtonRunnable);
        if (shouldFadeIn) {
            tocButton.show();
        } else {
            tocButton.postDelayed(hideToCButtonRunnable, TOC_BUTTON_HIDE_DELAY);
        }
    }

    private Runnable hideToCButtonRunnable = new Runnable() {
        @Override
        public void run() {
            tocButton.hide();
        }
    };

    /**
     * Convenience method for hiding all the content of a page.
     */
    private void hidePageContent() {


        //TODO
        //leadImagesHandler.hide();
        //searchBarHideHandler.setFadeEnabled(false);
        //pageLoadStrategy.onHidePageContent();
        //webView.setVisibility(View.INVISIBLE);

        storyList.setVisibility(View.INVISIBLE);

    }

    @Override
    public boolean onBackPressed() {
        if (tocHandler != null && tocHandler.isVisible()) {
            tocHandler.hide();
            return true;
        }
        if (closeFindInPage()) {
            return true;
        }


        //TODO
        //if (pageLoadStrategy.onBackPressed()) {
        //    return true;
        //}


        if (tabsProvider.onBackPressed()) {
            return true;
        }
        if (tabList.size() > 1) {
            // if we're at the end of the current tab's backstack, then pop the current tab.
            tabList.remove(tabList.size() - 1);
        }
        return false;
    }

    public LinkHandler getLinkHandler() {


        //TODO
        //return linkHandler;
        return null;


    }

    private void checkIfPageIsSaved() {
        if (getActivity() != null && getTitle() != null) {
            new SavedPageCheckTask(getTitle(), app) {
                @Override
                public void onFinish(Boolean exists) {
                    setPageSaved(exists);
                    setSavedPageCheckComplete(true);
                }
            }.execute();
        }
    }

    private void checkAndShowSelectTextOnboarding() {
        if (app.isFeatureSelectTextAndShareTutorialEnabled()
        &&  model.getPage().isArticle()
        &&  app.getOnboardingStateMachine().isSelectTextTutorialEnabled()) {
            showSelectTextOnboarding();
        }
    }

    private void showSelectTextOnboarding() {
        final View targetView = getView().findViewById(R.id.fragment_page_tool_tip_select_text_target);
        targetView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (getActivity() != null) {
                    ToolTipUtil.showToolTip(getActivity(),
                                            targetView, R.layout.inflate_tool_tip_select_text,
                                            ToolTip.Position.CENTER);
                    app.getOnboardingStateMachine().setSelectTextTutorial();
                }
            }
        }, TimeUnit.SECONDS.toMillis(1));
    }

    private void updateNavDrawerSelection() {
        if (isAdded()) {
            // TODO: define a Fragment host interface instead of assuming a cast is safe.
            ((PageActivity) getActivity()).updateNavDrawerSelection(this);
        }
    }

    private void initTabs() {
        if (Prefs.hasTabs()) {
            tabList.addAll(Prefs.getTabs());
        }

        if (tabList.isEmpty()) {
            tabList.add(new Tab());
        }
    }

    private void initPageScrollFunnel() {
        if (model.getPage() != null) {
            pageScrollFunnel = new PageScrollFunnel(app, model.getPage().getPageProperties().getPageId());
        }
    }

    private void closePageScrollFunnel() {

        /*
        if (pageScrollFunnel != null && webView.getContentHeight() > 0) {
            pageScrollFunnel.setViewportHeight(webView.getHeight());
            pageScrollFunnel.setPageHeight(webView.getContentHeight());
            pageScrollFunnel.logDone();
        }
        */

        pageScrollFunnel = null;
    }

    // TODO: don't assume host is PageActivity. Use Fragment callbacks pattern.
    private PageActivity getPageActivity() {
        return (PageActivity) getActivity();
    }

    @VisibleForTesting
    public void setPageLoadCallbacks(@Nullable PageLoadCallbacks pageLoadCallbacks) {
        this.pageLoadCallbacks = pageLoadCallbacks;
    }

    private class LongPressHandler extends PageActivityLongPressHandler
            implements PageLongPressHandler.WebViewContextMenuListener {
        LongPressHandler(@NonNull PageActivity activity) {
            super(activity);
        }

        @Override
        public Site getSite() {
            return model.getTitleOriginal().getSite();
        }
    }
}
