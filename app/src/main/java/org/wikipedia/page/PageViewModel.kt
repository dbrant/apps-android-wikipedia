package org.wikipedia.page

import org.wikipedia.dataclient.okhttp.OkHttpConnectionFactory
import org.wikipedia.history.HistoryEntry
import org.wikipedia.readinglist.database.ReadingListPage

class PageViewModel {

    var page: Page? = null
    var title: PageTitle? = null
    var curEntry: HistoryEntry? = null
    var readingListPage: ReadingListPage? = null
    var hasWatchlistExpiry = false
    var isWatched = false
    var forceNetwork = false
    val isInReadingList get() = readingListPage != null
    val cacheControl get() = if (forceNetwork) OkHttpConnectionFactory.CACHE_CONTROL_FORCE_NETWORK else OkHttpConnectionFactory.CACHE_CONTROL_NONE
}
