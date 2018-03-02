package org.wikipedia.server.wikidata;

import android.support.annotation.NonNull;

import org.apache.commons.lang3.StringUtils;
import org.wikipedia.dataclient.retrofit.RetrofitException;
import org.wikipedia.settings.RbSwitch;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Retrofit web service client for Wikidata PHP API.
 */
public class WdEntityService {
    private final WdEndpoints webService;

    public WdEntityService() {
        webService = WdEndpointsCache.INSTANCE.getWdEndpoints();
    }

    public void entitiesForTitle(String title, final EntityCallback cb) {
        Call<WdEntities> call = webService.getEntitiesForTitle(title);
        call.enqueue(new Callback<WdEntities>() {
            @Override
            public void onResponse(@NonNull Call<WdEntities> call, @NonNull Response<WdEntities> response) {
                if (response.isSuccessful() && response.body() != null) {
                    cb.success(response.body());
                } else {
                    Throwable throwable =  RetrofitException.httpError(response);
                    cb.failure(throwable);
                }
            }

            @Override
            public void onFailure(@NonNull Call<WdEntities> call, @NonNull Throwable t) {
                RbSwitch.INSTANCE.onRbRequestFailed(t);
                cb.failure(t);
            }
        });
    }

    public void entityLabelsForIds(List<String> titleList, final EntityCallback cb) {
        String titles = StringUtils.join(titleList, "|");
        Call<WdEntities> call = webService.getEntityLabelsForIds(titles);
        call.enqueue(new Callback<WdEntities>() {
            @Override
            public void onResponse(@NonNull Call<WdEntities> call, @NonNull Response<WdEntities> response) {
                if (response.isSuccessful() && response.body() != null) {
                    cb.success(response.body());
                } else {
                    Throwable throwable = RetrofitException.httpError(response);
                    cb.failure(throwable);
                }
            }

            @Override
            public void onFailure(@NonNull Call<WdEntities> call, @NonNull Throwable t) {
                RbSwitch.INSTANCE.onRbRequestFailed(t);
                cb.failure(t);
            }
        });
    }

    public interface EntityCallback {
        void success(@NonNull WdEntities entities);
        void failure(@NonNull Throwable error);
    }

    interface WdEndpoints {
        // FIXME: add proper lang parameter
        @GET("/w/api.php?action=wbgetentities&format=json&props=claims%7Cdescriptions&languages=en&sites=enwiki")
        Call<WdEntities> getEntitiesForTitle(@Query("titles") String title);

        @GET("/w/api.php?action=wbgetentities&format=json&props=labels&languages=en")
        Call<WdEntities> getEntityLabelsForIds(@Query("ids") String titles);
    }
}
