package org.wikipedia.page;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;
import org.json.JSONObject;
import org.mediawiki.api.json.ApiException;
import org.wikipedia.Constants;
import org.wikipedia.R;
import org.wikipedia.WikipediaApp;
import org.wikipedia.bridge.CommunicationBridge;
import org.wikipedia.database.contract.PageImageHistoryContract;
import org.wikipedia.edit.EditHandler;
import org.wikipedia.edit.EditSectionActivity;
import org.wikipedia.history.HistoryEntry;
import org.wikipedia.login.User;
import org.wikipedia.offline.OfflineHelper;
import org.wikipedia.page.bottomcontent.BottomContentHandler;
import org.wikipedia.page.bottomcontent.BottomContentInterface;
import org.wikipedia.page.leadimages.LeadImagesHandler;
import org.wikipedia.pageimages.PageImage;
import org.wikipedia.pageimages.PageImagesTask;
import org.wikipedia.savedpages.LoadSavedPageTask;
import org.wikipedia.server.PageLead;
import org.wikipedia.server.PageRemaining;
import org.wikipedia.server.PageServiceFactory;
import org.wikipedia.server.ServiceError;
import org.wikipedia.util.DeviceUtil;
import org.wikipedia.util.DimenUtil;
import org.wikipedia.util.L10nUtil;
import org.wikipedia.util.ResourceUtil;
import org.wikipedia.util.log.L;
import org.wikipedia.views.ObservableWebView;
import org.wikipedia.views.SwipeRefreshLayoutWithScroll;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.wikipedia.util.DimenUtil.calculateLeadImageWidth;
import static org.wikipedia.util.L10nUtil.getStringsForArticleLanguage;

/**
 * Our old page load strategy, which uses the JSON MW API directly and loads a page in multiple steps:
 * First it loads the lead section (sections=0).
 * Then it loads the remaining sections (sections=1-).
 * <p/>
 * This class tracks:
 * - the states the page loading goes through,
 * - a backstack of pages and page positions visited,
 * - and many handlers.
 */
public class PageOfflineDataClient implements PageLoadStrategy {
    private interface ErrorCallback {
        void call(@Nullable Throwable error);
    }

    private static final String BRIDGE_PAYLOAD_SAVED_PAGE = "savedPage";

    private boolean loading;

    /**
     * List of lightweight history items to serve as the backstack for this fragment.
     * Since the list consists of Parcelable objects, it can be saved and restored from the
     * savedInstanceState of the fragment.
     */
    @NonNull private List<PageBackStackItem> backStack = new ArrayList<>();

    @NonNull private final SequenceNumber sequenceNumber = new SequenceNumber();

    /**
     * The y-offset position to which the page will be scrolled once it's fully loaded
     * (or loaded to the point where it can be scrolled to the correct position).
     */
    private int stagedScrollY;
    private String sectionTargetFromTitle;

    // copied fields
    private PageViewModel model;
    private PageFragment fragment;
    private CommunicationBridge bridge;
    private ObservableWebView webView;
    private SwipeRefreshLayoutWithScroll refreshView;
    @NonNull private final WikipediaApp app = WikipediaApp.getInstance();
    private LeadImagesHandler leadImagesHandler;
    private PageToolbarHideHandler toolbarHideHandler;
    private EditHandler editHandler;

    private BottomContentInterface bottomContentHandler;

    @Override
    @SuppressWarnings("checkstyle:parameternumber")
    public void setUp(@NonNull PageViewModel model,
                      @NonNull PageFragment fragment,
                      @NonNull SwipeRefreshLayoutWithScroll refreshView,
                      @NonNull ObservableWebView webView,
                      @NonNull CommunicationBridge bridge,
                      @NonNull PageToolbarHideHandler toolbarHideHandler,
                      @NonNull LeadImagesHandler leadImagesHandler,
                      @NonNull List<PageBackStackItem> backStack) {
        this.model = model;
        this.fragment = fragment;
        this.refreshView = refreshView;
        this.webView = webView;
        this.bridge = bridge;
        this.toolbarHideHandler = toolbarHideHandler;
        this.leadImagesHandler = leadImagesHandler;

        setUpBridgeListeners();

        bottomContentHandler = new BottomContentHandler(fragment, bridge, webView,
                fragment.getLinkHandler(),
                (ViewGroup) fragment.getView().findViewById(R.id.bottom_content_container));

        this.backStack = backStack;

        // if we already have pages in the backstack (whether it's from savedInstanceState, or
        // from being stored in the activity's fragment backstack), then load the topmost page
        // on the backstack.
        loadFromBackStack();
    }

    @Override
    public void load(boolean pushBackStack, @NonNull Cache cachePreference, int stagedScrollY) {
        if (pushBackStack) {
            // update the topmost entry in the backstack, before we start overwriting things.
            updateCurrentBackStackItem();
            pushBackStack();
        }

        loading = true;

        // increment our sequence number, so that any async tasks that depend on the sequence
        // will invalidate themselves upon completion.
        sequenceNumber.increase();

        fragment.updatePageInfo(null);

        // kick off an event to the WebView that will cause it to clear its contents,
        // and then report back to us when the clearing is complete, so that we can synchronize
        // the transitions of our native components to the new page content.
        // The callback event from the WebView will then call the loadOnWebViewReady()
        // function, which will continue the loading process.
        leadImagesHandler.hide();
        bottomContentHandler.hide();
        fragment.getSearchBarHideHandler().setFadeEnabled(false);
        try {
            JSONObject wrapper = new JSONObject();
            // whatever we pass to this event will be passed back to us by the WebView!
            wrapper.put("sequence", sequenceNumber.get());
            wrapper.put("cachePreference", cachePreference.name());
            wrapper.put("stagedScrollY", stagedScrollY);
            bridge.sendMessage("beginNewPage", wrapper);
        } catch (JSONException e) {
            L.logRemoteErrorIfProd(e);
        }
    }

    @Override
    public boolean isLoading() {
        return loading;
    }

    @Override
    public void loadFromBackStack() {
        if (backStack.isEmpty()) {
            return;
        }
        PageBackStackItem item = backStack.get(backStack.size() - 1);
        // display the page based on the backstack item, stage the scrollY position based on
        // the backstack item.
        fragment.loadPage(item.getTitle(), item.getHistoryEntry(), Cache.PREFERRED, false,
                item.getScrollY());
        L.d("Loaded page " + item.getTitle().getDisplayText() + " from backstack");
    }

    @Override
    public void updateCurrentBackStackItem() {
        if (backStack.isEmpty()) {
            return;
        }
        PageBackStackItem item = backStack.get(backStack.size() - 1);
        item.setScrollY(webView.getScrollY());
    }

    @Override
    public void setBackStack(@NonNull List<PageBackStackItem> backStack) {
        this.backStack = backStack;
    }

    @Override
    public boolean popBackStack() {
        if (!backStack.isEmpty()) {
            backStack.remove(backStack.size() - 1);
        }

        if (!backStack.isEmpty()) {
            loadFromBackStack();
            return true;
        }

        return false;
    }

    @Override public boolean backStackEmpty() {
        return backStack.isEmpty();
    }

    @Override
    public void onHidePageContent() {
        bottomContentHandler.hide();
    }

    @Override
    public void setEditHandler(EditHandler editHandler) {
        this.editHandler = editHandler;
    }

    @Override
    public void backFromEditing(Intent data) {
    }

    @Override
    public void layoutLeadImage() {
        leadImagesHandler.beginLayout(new LeadImagesHandler.OnLeadImageLayoutListener() {
            @Override
            public void onLayoutComplete(int sequence) {
                if (fragment.isAdded()) {
                    toolbarHideHandler.setFadeEnabled(leadImagesHandler.isLeadImageEnabled());
                }
            }
        }, sequenceNumber.get());
    }

    private void setUpBridgeListeners() {
        bridge.addListener("onBeginNewPage", new SynchronousBridgeListener() {
            @Override
            public void onMessage(JSONObject payload) {
                try {
                    stagedScrollY = payload.getInt("stagedScrollY");
                    loadOnWebViewReady(Cache.valueOf(payload.getString("cachePreference")));
                } catch (JSONException e) {
                    L.logRemoteErrorIfProd(e);
                }
            }
        });
        bridge.addListener("pageLoadComplete", new SynchronousBridgeListener() {
            @Override
            public void onMessage(JSONObject payload) {
                // Do any other stuff that should happen upon page load completion...
                if (fragment.callback() != null) {
                    fragment.callback().onPageUpdateProgressBar(false, true, 0);
                }

                // trigger layout of the bottom content
                // Check to see if the page title has changed (e.g. due to following a redirect),
                // because if it has then the handler needs the new title to make sure it doesn't
                // accidentally display the current article as a "read more" suggestion
                bottomContentHandler.setTitle(model.getTitle());
                bottomContentHandler.beginLayout();
            }
        });
        bridge.addListener("pageInfo", new CommunicationBridge.JSEventListener() {
            @Override
            public void onMessage(String message, JSONObject payload) {
                if (fragment.isAdded()) {
                    PageInfo pageInfo = PageInfoUnmarshaller.unmarshal(model.getTitle(),
                            model.getTitle().getWikiSite(), payload);
                    fragment.updatePageInfo(pageInfo);
                }
            }
        });
    }

    private void loadOnWebViewReady(Cache cachePreference) {
        // stage any section-specific link target from the title, since the title may be
        // replaced (normalized)
        sectionTargetFromTitle = model.getTitle().getFragment();

        L10nUtil.setupDirectionality(model.getTitle().getWikiSite().languageCode(),
                Locale.getDefault().getLanguage(), bridge);

        L.d("Loading page from ZIM: " + model.getTitleOriginal().getDisplayText());

        // create a Page object from ZIM data

        String html = OfflineHelper.getHtml(model.getTitle().getDisplayText());
        Section section = new Section(0, 0, "", "", html);
        List<Section> sections = new ArrayList<>();
        sections.add(section);
        Page page = new Page(model.getTitle(), sections, new PageProperties(model.getTitle()));

        model.setPage(page);
        editHandler.setPage(model.getPage());

        // Update our history entry, in case the Title was changed (i.e. normalized)
        HistoryEntry curEntry = model.getCurEntry();
        model.setCurEntry(
                new HistoryEntry(model.getTitle(), curEntry.getSource()));

        enqueuePageContents();

        loading = false;

        // on error:
        // fragment.onPageLoadError(Exception);
    }

    private boolean isFirstPage() {
        return backStack.size() <= 1 && !webView.canGoBack();
    }

    /**
     * Push the current page title onto the backstack.
     */
    private void pushBackStack() {
        PageBackStackItem item = new PageBackStackItem(model.getTitleOriginal(), model.getCurEntry());
        backStack.add(item);
    }

    private void enqueuePageContents() {
        leadImagesHandler.beginLayout(new LeadImagesHandler.OnLeadImageLayoutListener() {
            @Override
            public void onLayoutComplete(int sequence) {
                if (!fragment.isAdded() || !sequenceNumber.inSync(sequence)) {
                    return;
                }
                toolbarHideHandler.setFadeEnabled(leadImagesHandler.isLeadImageEnabled());
                displayAllSections();
            }
        }, sequenceNumber.get());
    }

    private void displayAllSections() {
        Page page = model.getPage();

        sendMarginPayload();
        sendZimPayload(page);
        sendMiscPayload(page);

        if (webView.getVisibility() != View.VISIBLE) {
            webView.setVisibility(View.VISIBLE);
        }

        refreshView.setRefreshing(false);

        if (fragment.isAdded()) {
            if (fragment.callback() != null) {
                fragment.callback().onPageUpdateProgressBar(true, true, 0);
            }
            fragment.setupToC(model, isFirstPage());
            fragment.onPageLoadComplete();
        }
    }

    private void sendMarginPayload() {
        JSONObject marginPayload = marginPayload();
        bridge.sendMessage("setMargins", marginPayload);
    }

    private JSONObject marginPayload() {
        int horizontalMargin = DimenUtil.roundedPxToDp(getDimension(R.dimen.content_margin));
        int verticalMargin = DimenUtil.roundedPxToDp(getDimension(R.dimen.activity_vertical_margin));
        try {
            return new JSONObject()
                    .put("marginTop", verticalMargin)
                    .put("marginLeft", horizontalMargin)
                    .put("marginRight", horizontalMargin);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendZimPayload(Page page) {
        JSONObject zimPayload = zimPayload(page);
        bridge.sendMessage("displayFromZim", zimPayload);
        L.d("Sent message 'displayFromZim' for page: " + page.getDisplayTitle());
    }

    private JSONObject zimPayload(Page page) {
        SparseArray<String> localizedStrings = localizedStrings(page);
        try {
            JSONObject payload = new JSONObject()
                    .put("sequence", sequenceNumber.get())
                    .put("title", page.getDisplayTitle())
                    .put("zimhtml", page.getSections().get(0).toJSON())
                    .put("string_table_infobox", localizedStrings.get(R.string.table_infobox))
                    .put("string_table_other", localizedStrings.get(R.string.table_other))
                    .put("string_table_close", localizedStrings.get(R.string.table_close))
                    .put("string_expand_refs", localizedStrings.get(R.string.expand_refs))
                    .put("isBeta", app.isPreProdRelease()) // True for any non-production release type
                    .put("siteLanguage", model.getTitle().getWikiSite().languageCode())
                    .put("siteBaseUrl", model.getTitle().getWikiSite().scheme() + "://" + model.getTitle().getWikiSite().host())
                    .put("isMainPage", page.isMainPage())
                    .put("fromRestBase", false)
                    .put("isNetworkMetered", DeviceUtil.isNetworkMetered(app))
                    .put("apiLevel", Build.VERSION.SDK_INT);

            if (sectionTargetFromTitle != null) {
                //if we have a section to scroll to (from our PageTitle):
                payload.put("fragment", sectionTargetFromTitle);
            } else if (!TextUtils.isEmpty(model.getTitle().getFragment())) {
                // It's possible, that the link was a redirect and the new title has a fragment
                // scroll to it, if there was no fragment so far
                payload.put("fragment", model.getTitle().getFragment());
            }

            //give it our expected scroll position, in case we need the page to be pre-scrolled upon loading.
            payload.put("scrollY", (int) (stagedScrollY / DimenUtil.getDensityScalar()));

            return payload;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private SparseArray<String> localizedStrings(Page page) {
        return getStringsForArticleLanguage(page.getTitle(),
                ResourceUtil.getIdArray(fragment.getContext(), R.array.page_localized_string_ids));
    }


    private void sendMiscPayload(Page page) {
        JSONObject miscPayload = miscPayload(page);
        bridge.sendMessage("setPageProtected", miscPayload);
    }

    private JSONObject miscPayload(Page page) {
        try {
            return new JSONObject()
                    .put("noedit", !isPageEditable(page)) // Controls whether edit pencils are visible.
                    .put("protect", page.isProtected());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isPageEditable(Page page) {
        return (User.isLoggedIn() || !isAnonEditingDisabled())
                && !page.isFilePage()
                && !page.isMainPage();
    }

    private boolean isAnonEditingDisabled() {
        return getRemoteConfig().optBoolean("disableAnonEditing", false);
    }

    private JSONObject getRemoteConfig() {
        return app.getRemoteConfig().getConfig();
    }

    private float getDimension(@DimenRes int id) {
        return getResources().getDimension(id);
    }

    private Resources getResources() {
        return fragment.getResources();
    }

    private abstract class SynchronousBridgeListener implements CommunicationBridge.JSEventListener {
        private static final String BRIDGE_PAYLOAD_SEQUENCE = "sequence";

        @Override
        public void onMessage(String message, JSONObject payload) {
            if (fragment.isAdded() && inSync(payload)) {
                onMessage(payload);
            }
        }

        protected abstract void onMessage(JSONObject payload);

        private boolean inSync(JSONObject payload) {
            return sequenceNumber.inSync(payload.optInt(BRIDGE_PAYLOAD_SEQUENCE,
                    sequenceNumber.get() - 1));
        }
    }

    /**
     * Monotonically increasing sequence number to maintain synchronization when loading page
     * content asynchronously between the Java and JavaScript layers, as well as between synchronous
     * methods and asynchronous callbacks on the UI thread.
     */
    private static class SequenceNumber {
        private int sequence;

        void increase() {
            ++sequence;
        }

        int get() {
            return sequence;
        }

        boolean inSync(int sequence) {
            return this.sequence == sequence;
        }
    }
}
