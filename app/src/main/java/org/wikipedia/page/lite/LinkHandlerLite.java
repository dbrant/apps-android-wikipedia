package org.wikipedia.page.lite;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import org.wikipedia.Site;
import org.wikipedia.WikipediaApp;
import org.wikipedia.page.LinkMovementMethodExt;
import org.wikipedia.page.PageTitle;

import static org.wikipedia.util.UriUtil.handleExternalLink;

/**
 * Handles any html links coming from a {@link org.wikipedia.page.PageFragment}
 */
public abstract class LinkHandlerLite implements LinkMovementMethodExt.UrlHandler {
    private final Context context;

    public LinkHandlerLite(Context context) {
        this.context = context;
    }

    public abstract void onPageLinkClicked(String anchor);

    public abstract void onInternalLinkClicked(PageTitle title);

    @Override
    public void onUrlClick(String href) {
        if (href.startsWith("//")) {
            // That's a protocol specific link! Make it https!
            href = "https:" + href;
        }
        Log.d("Wikipedia", "Link clicked was " + href);
        if (href.startsWith("/wiki/")) {
            PageTitle title = getSite().titleForInternalLink(href);
            onInternalLinkClicked(title);
        } else if (href.startsWith("#")) {
            onPageLinkClicked(href.substring(1));
        } else {
            Uri uri = Uri.parse(href);
            String authority = uri.getAuthority();
            // FIXME: Make this more complete, only to not handle URIs that contain unsupported actions
            if (authority != null && Site.isSupportedSite(authority) && uri.getPath().startsWith("/wiki/")) {
                Site site = new Site(authority);
                PageTitle title = site.titleForUri(uri);
                onInternalLinkClicked(title);
            } else {
                // if it's a /w/ URI, turn it into a full URI and go external
                if (href.startsWith("/w/")) {
                    href = String.format("%1$s://%2$s", WikipediaApp.getInstance().getNetworkProtocol(), getSite().getDomain()) + href;
                }
                handleExternalLink(context, Uri.parse(href));
            }
        }
    }

    public abstract Site getSite();
}
