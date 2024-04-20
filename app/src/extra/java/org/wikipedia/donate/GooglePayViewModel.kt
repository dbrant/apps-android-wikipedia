package org.wikipedia.donate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.wallet.PaymentData
import com.google.android.gms.wallet.PaymentDataRequest
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
import java.text.NumberFormat
import java.util.Locale

class GooglePayViewModel : ViewModel() {
    val uiState = MutableStateFlow(Resource<DonationConfig>())
    var donationConfig: DonationConfig? = null
    var paymentMethod: PaymentMethod? = null

    val currencyFormat: NumberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())

    val currencyCode get() = currencyFormat.currency?.currencyCode ?: "USD"

    val currencySymbol get() = currencyFormat.currency?.symbol ?: "$"

    val transactionFee: Float get() = donationConfig?.currencyTransactionFees?.get(currencyCode)
        ?: donationConfig?.currencyTransactionFees?.get("default") ?: 0f

    init {
        // TODO: is this right?
        currencyFormat.minimumFractionDigits = 0
        currencyFormat.maximumFractionDigits = 2

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

            donationConfig = donationConfigCall.await()
            val paymentMethods = paymentMethodsCall.await().response!!.paymentMethods
            paymentMethod = paymentMethods.find { it.type == "paywithgoogle" }!!

            uiState.value = Resource.Success(donationConfig!!)
        }
    }

    fun getPaymentDataRequest(amount: Float): PaymentDataRequest {
        return PaymentDataRequest.fromJson(GooglePayComponent.getPaymentDataRequestJson(amount,
            currencyCode, paymentMethod?.configuration?.merchantId, paymentMethod?.configuration?.gatewayMerchantId).toString())
    }

    fun submit(paymentData: PaymentData) {
        viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
            uiState.value = Resource.Error(throwable)
        }) {
            uiState.value = Resource.Loading()

            val token = GooglePayComponent.findToken(paymentData)

            //ServiceFactory.get(WikiSite(GooglePayComponent.PAYMENTS_API_URL))
            //    .submitPayment()

            uiState.value = DonateSuccess()
        }
    }

    class DonateSuccess : Resource<DonationConfig>()
}
