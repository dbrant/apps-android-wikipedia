package org.wikipedia.donate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.wikipedia.dataclient.ServiceFactory
import org.wikipedia.dataclient.WikiSite
import org.wikipedia.dataclient.donate.DonationConfig
import org.wikipedia.dataclient.donate.DonationConfigHelper
import org.wikipedia.dataclient.donate.PaymentMethod
import org.wikipedia.util.GeoUtil
import org.wikipedia.util.Resource

class GooglePayViewModel : ViewModel() {
    val uiState = MutableStateFlow(Resource<Pair<List<PaymentMethod>, DonationConfig>>())

    init {
        load()
    }

    fun load() {
        viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
            uiState.value = Resource.Error(throwable)
        }) {
            uiState.value = Resource.Loading()

            val paymentMethodsCall = async { ServiceFactory.get(WikiSite(GooglePayComponent.PAYMENTS_API_URL))
                .getPaymentMethods(GeoUtil.geoIPCountry.orEmpty()) }
            val donationConfigCall = async { DonationConfigHelper.getConfig() }

            uiState.value = Resource.Success(Pair(paymentMethodsCall.await().response!!.paymentMethods, donationConfigCall.await()!!))
        }
    }
}
