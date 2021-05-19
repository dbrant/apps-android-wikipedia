package org.wikipedia.feed.whodied

import android.content.Context
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import org.wikipedia.dataclient.Service
import org.wikipedia.dataclient.ServiceFactory
import org.wikipedia.dataclient.WikiSite
import org.wikipedia.dataclient.mwapi.MwQueryResponse
import org.wikipedia.feed.dataclient.FeedClient
import org.wikipedia.page.PageTitle
import org.wikipedia.util.ImageUrlUtil.getUrlForPreferredSize
import org.wikipedia.util.log.L.e
import java.util.*

class WhoDiedClient : FeedClient {
    private val disposables = CompositeDisposable()

    override fun request(context: Context, wiki: WikiSite, age: Int, cb: FeedClient.Callback) {
        cancel()
        disposables.add(ServiceFactory.get(wiki).listOfDeaths
                .subscribeOn(Schedulers.io())
                .flatMap { responseBody: ResponseBody ->
                    val document = Jsoup.parse(responseBody.string())
                    val elements = document.select("h3 + ul > li")
                    val titles = mutableListOf<String>()
                    for (el in elements) {
                        val links = el.getElementsByTag("a")
                        if (links.isNotEmpty()) {
                            val title = links.first().attr("title")
                            val href = links.first().attr("href")
                            if (!href.contains("&redlink")) {
                                titles.add(title)
                            }
                        }
                        if (titles.size > 10) {
                            break
                        }
                    }
                    ServiceFactory.get(wiki).getThumbnailAndDescription(titles.joinToString("|"))
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response: MwQueryResponse ->
                    val itemCards: MutableList<WhoDiedItemCard> = ArrayList()
                    for (page in response.query()!!.pages()!!) {
                        val title = PageTitle(page.title(), wiki)
                        title.description = page.description()
                        title.thumbUrl = if (page.thumbUrl() != null) getUrlForPreferredSize(page.thumbUrl()!!, Service.PREFERRED_THUMB_SIZE) else null
                        itemCards.add(WhoDiedItemCard(title))
                    }
                    cb.success(listOf(WhoDiedCard(itemCards, wiki)))
                }
                ) { throwable: Throwable? ->
                    e(throwable)
                    cb.success(emptyList())
                })
    }

    override fun cancel() {
        disposables.clear()
    }
}
