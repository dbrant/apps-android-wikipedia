package org.wikipedia.donate

import android.app.Activity
import com.google.android.gms.wallet.IsReadyToPayRequest
import com.google.android.gms.wallet.PaymentData
import com.google.android.gms.wallet.PaymentsClient
import com.google.android.gms.wallet.Wallet
import com.google.android.gms.wallet.WalletConstants
import kotlinx.coroutines.tasks.await
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

internal object GooglePayComponent {

    const val PAYMENTS_API_URL = "https://payments.wikimedia.org"

    private val allAllowedCardNetworks: List<String> = listOf("VISA", "MASTERCARD", "AMEX", "DISCOVER", "JCB", "INTERAC")
    private val allAllowedAuthMethods: List<String> = listOf("PAN_ONLY", "CRYPTOGRAM_3DS")

    val baseCardPaymentMethod = JSONObject().apply {
        put("type", "CARD")
        put("parameters", JSONObject().apply {
            put("allowedCardNetworks", JSONArray(allAllowedCardNetworks))
            put("allowedAuthMethods", JSONArray(allAllowedAuthMethods))
        })
    }
    private val googlePayBaseConfiguration = JSONObject().apply {
        put("apiVersion", 2)
        put("apiVersionMinor", 0)
        put("allowedPaymentMethods", JSONArray().put(baseCardPaymentMethod))
    }

    fun findToken(paymentData: PaymentData): String {
        return JSONObject(paymentData.toJson())
            .getJSONObject("paymentMethodData")
            .getJSONObject("tokenizationData")
            .getString("token")
    }

    fun createPaymentsClient(activity: Activity): PaymentsClient {
        val walletOptions = Wallet.WalletOptions.Builder()
            .setEnvironment(WalletConstants.ENVIRONMENT_TEST).build()
        return Wallet.getPaymentsClient(activity, walletOptions)
    }

    suspend fun isGooglePayAvailable(activity: Activity): Boolean {
        val readyToPayRequest = IsReadyToPayRequest.fromJson(googlePayBaseConfiguration.toString())
        val paymentsClient = createPaymentsClient(activity)
        val readyToPayTask = paymentsClient.isReadyToPay(readyToPayRequest)
        readyToPayTask.await()
        return readyToPayTask.result
    }

    fun onGooglePayButtonClicked(activity: Activity) {
        activity.startActivity(GooglePayActivity.newIntent(activity))
    }

    fun getPaymentDataRequestJson(
        amount: Float,
        currencyCode: String,
        merchantId: String?,
        gatewayMerchantId: String?
    ): JSONObject {
        val merchantInfo = JSONObject().apply {
            put("merchantName", "Wikimedia Foundation")
            put("merchantId", merchantId)
        }

        val transactionInfo = JSONObject().apply {
            put("totalPrice", amount.toString())
            put("totalPriceStatus", "FINAL")
            put("currencyCode", currencyCode)
        }

        val tokenizationSpecification = JSONObject().apply {
            put("type", "PAYMENT_GATEWAY")
            put(
                "parameters", JSONObject(
                    mapOf(
                        "gateway" to "adyen",
                        "gatewayMerchantId" to gatewayMerchantId
                    )
                )
            )
        }

        val cardPaymentMethod = JSONObject().apply {
            put("type", "CARD")
            put("tokenizationSpecification", tokenizationSpecification)
            put("parameters", JSONObject().apply {
                put("allowedCardNetworks", JSONArray(allAllowedCardNetworks))
                put("allowedAuthMethods", JSONArray(allAllowedAuthMethods))
                put("billingAddressRequired", true)
                put("billingAddressParameters", JSONObject(mapOf("format" to "FULL")))
            })
        }

        val paymentDataRequestJson = JSONObject(googlePayBaseConfiguration.toString()).apply {
            put("allowedPaymentMethods", JSONArray().put(cardPaymentMethod))
            put("transactionInfo", transactionInfo)
            put("merchantInfo", merchantInfo)
        }

        return paymentDataRequestJson
    }
}
