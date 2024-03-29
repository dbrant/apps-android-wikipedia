package org.wikipedia.donate

import android.location.Location
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.wikipedia.analytics.eventplatform.WatchlistAnalyticsHelper
import org.wikipedia.auth.AccountUtil
import org.wikipedia.database.AppDatabase
import org.wikipedia.dataclient.ServiceFactory
import org.wikipedia.extensions.parcelable
import org.wikipedia.history.HistoryEntry
import org.wikipedia.page.PageTitle
import org.wikipedia.settings.Prefs
import org.wikipedia.util.Resource
import org.wikipedia.util.log.L
import org.wikipedia.watchlist.WatchlistExpiry

class DonateViewModel(bundle: Bundle) : ViewModel() {
    private val _uiState = MutableStateFlow<Resource<Boolean>>(Resource.Loading())
    val uiState = _uiState.asStateFlow()

    init {
        loadContent()
    }

    private fun loadContent() {
        viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
            _uiState.value = Resource.Error(throwable)
        }) {
            _uiState.value = Resource.Loading()

            val summaryCall = async { ServiceFactory.getRest(pageTitle.wikiSite)
                .getSummaryResponseSuspend(pageTitle.prefixedText, null, null, null, null, null) }

            val watchedCall = async { if (fromPlaces && AccountUtil.isLoggedIn) ServiceFactory.get(pageTitle.wikiSite).getWatchedStatus(pageTitle.prefixedText) else null }

            val response = summaryCall.await()
            val summary = response.body()!!
            // Rebuild our PageTitle, since it may have been redirected or normalized.
            val oldFragment = pageTitle.fragment
            pageTitle = PageTitle(
                    summary.apiTitle, pageTitle.wikiSite, summary.thumbnailUrl,
                    summary.description, summary.displayTitle
            )

            // check if our URL was redirected, which might include a URL fragment that leads
            // to a specific section in the target article.
            if (!response.raw().request.url.fragment.isNullOrEmpty()) {
                pageTitle.fragment = response.raw().request.url.fragment
            } else if (!oldFragment.isNullOrEmpty()) {
                pageTitle.fragment = oldFragment
            }

            if (fromPlaces) {
                isWatched = watchedCall.await()?.query?.firstPage()?.watched ?: false
                val readingList = AppDatabase.instance.readingListPageDao().findPageInAnyList(pageTitle)
                isInReadingList = readingList != null
            }

            if (location == null) {
                location = summary.coordinates
            }

            _uiState.value = Resource.Success(true)
        }
    }

}
