package org.wikipedia.server.wikidata;

import org.wikipedia.dataclient.WikiSite;
import org.wikipedia.dataclient.retrofit.RetrofitFactory;

import retrofit2.Retrofit;

/**
 * It's good to cache the Retrofit web service since it's a memory intensive object.
 * Keep the same instance around as long as we're dealing with the same domain.
 */
public final class WdEndpointsCache {
    public static final WdEndpointsCache INSTANCE = new WdEndpointsCache();

    private WdEntityService.WdEndpoints cachedWebService;
    private Retrofit retrofit;

    private WdEndpointsCache() {
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public WdEntityService.WdEndpoints getWdEndpoints() {
        if (cachedWebService == null) {
            cachedWebService = createService();
        }
        return cachedWebService;
    }

    private WdEntityService.WdEndpoints createService() {
        WikiSite site = new WikiSite("wikidata.org");
        retrofit = RetrofitFactory.newInstance(site);
        return retrofit.create(WdEntityService.WdEndpoints.class);
    }
}
