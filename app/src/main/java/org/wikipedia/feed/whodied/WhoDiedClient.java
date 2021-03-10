package org.wikipedia.feed.whodied;

import android.content.Context;

import androidx.annotation.NonNull;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.wikipedia.dataclient.ServiceFactory;
import org.wikipedia.dataclient.WikiSite;
import org.wikipedia.feed.dataclient.FeedClient;
import org.wikipedia.page.PageTitle;
import org.wikipedia.util.log.L;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class WhoDiedClient implements FeedClient {
    private final CompositeDisposable disposables = new CompositeDisposable();

    @Override public void request(@NonNull Context context, @NonNull final WikiSite wiki, int age,
                                  @NonNull final FeedClient.Callback cb) {
        cancel();
        disposables.add(ServiceFactory.get(wiki).getListOfDeaths()
                .subscribeOn(Schedulers.io())
                .map(responseBody -> {
                    Document document = Jsoup.parse(responseBody.string());
                    Elements elements = document.select("h3 + ul > li");

                    List<PageTitle> titles = new ArrayList<>();
                    while (elements.size() > 0) {
                        Element el = elements.remove(0);
                        Elements links = el.getElementsByTag("a");
                        if (links.size() > 0) {
                            String title = links.first().attr("title");
                            String href = links.first().attr("href");
                            if (!href.contains("&redlink")) {
                                titles.add(new PageTitle(title, wiki));
                            }
                        }
                    }
                    return titles;
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(titles -> {
                            List<WhoDiedItemCard> itemCards = new ArrayList<>();
                            for (PageTitle title : titles) {
                                itemCards.add(new WhoDiedItemCard(title));
                            }
                            cb.success(Collections.singletonList(new WhoDiedCard(itemCards, wiki)));
                        },
                        throwable -> {
                            L.e(throwable);
                            cb.success(Collections.emptyList());
                        }));
    }

    @Override public void cancel() {
        disposables.clear();
    }
}
