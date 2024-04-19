package org.wikipedia.donate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.wikipedia.dataclient.donate.DonationConfig
import org.wikipedia.dataclient.donate.DonationConfigHelper
import org.wikipedia.dataclient.donate.PaymentMethod
import org.wikipedia.util.Resource

class GooglePayViewModel : ViewModel() {
    val uiState = MutableStateFlow(Resource<Pair<List<PaymentMethod>, DonationConfig>>())
    var donationConfig: DonationConfig? = null

    init {
        load()
    }

    fun load() {
        viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
            uiState.value = Resource.Error(throwable)
        }) {
            uiState.value = Resource.Loading()

            //val paymentMethodsCall = async { ServiceFactory.get(WikiSite(GooglePayComponent.PAYMENTS_API_URL))
            //    .getPaymentMethods(GeoUtil.geoIPCountry.orEmpty()) }

            val donationConfigCall = async { DonationConfigHelper.getConfig() }

            donationConfig = donationConfigCall.await()
            uiState.value = Resource.Success(Pair(
                emptyList(), // paymentMethodsCall.await().response!!.paymentMethods,
                donationConfig!!))
        }
    }
}
