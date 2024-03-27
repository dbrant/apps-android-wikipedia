package org.wikipedia.settings

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.text.method.LinkMovementMethodCompat
import androidx.core.view.descendants
import androidx.core.view.isVisible
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.wallet.AutoResolveHelper
import com.google.android.gms.wallet.IsReadyToPayRequest
import com.google.android.gms.wallet.PaymentData
import com.google.android.gms.wallet.PaymentDataRequest
import com.google.android.gms.wallet.PaymentsClient
import com.google.android.gms.wallet.Wallet
import com.google.android.gms.wallet.WalletConstants
import com.google.android.gms.wallet.button.ButtonConstants
import com.google.android.gms.wallet.button.ButtonOptions
import org.json.JSONArray
import org.json.JSONObject
import org.wikipedia.BuildConfig
import org.wikipedia.R
import org.wikipedia.WikipediaApp
import org.wikipedia.activity.BaseActivity
import org.wikipedia.databinding.ActivityAboutBinding
import org.wikipedia.richtext.setHtml
import org.wikipedia.util.FeedbackUtil
import org.wikipedia.util.log.L

class AboutActivity : BaseActivity() {

    private lateinit var binding: ActivityAboutBinding




    private lateinit var paymentsClient: PaymentsClient

    private val baseCardPaymentMethod = JSONObject().apply {
        put("type", "CARD")
        put("parameters", JSONObject().apply {
            put("allowedCardNetworks", JSONArray(listOf("VISA", "MASTERCARD")))
            put("allowedAuthMethods", JSONArray(listOf("PAN_ONLY", "CRYPTOGRAM_3DS")))
        })
    }
    private val googlePayBaseConfiguration = JSONObject().apply {
        put("apiVersion", 2)
        put("apiVersionMinor", 0)
        put("allowedPaymentMethods",  JSONArray().put(baseCardPaymentMethod))
    }

    private val tokenizationSpecification = JSONObject().apply {
        put("type", "PAYMENT_GATEWAY")
        put("parameters", JSONObject(mapOf(
            "gateway" to "adyen",
            "gatewayMerchantId" to "WikimediaDonations")))
    }

    private val cardPaymentMethod = JSONObject().apply {
        put("type", "CARD")
        put("tokenizationSpecification", tokenizationSpecification)
        put("parameters", JSONObject().apply {
            put("allowedCardNetworks", JSONArray(listOf("VISA", "MASTERCARD")))
            put("allowedAuthMethods", JSONArray(listOf("PAN_ONLY", "CRYPTOGRAM_3DS")))
            put("billingAddressRequired", true)
            put("billingAddressParameters", JSONObject(mapOf("format" to "FULL")))
        })
    }

    private val transactionInfo = JSONObject().apply {
        put("totalPrice", "2.00")
        put("totalPriceStatus", "FINAL")
        put("currencyCode", "USD")
    }

    // TODO: this should be the Google Merchant ID of WMF, separate from the Merchant ID within the payment gateway.
    private val merchantInfo = JSONObject().apply {
        put("merchantName", "Defiant Technologies, LLC.")
        put("merchantId", "BCR2DN4TQG4ZTFKI")
    }
    private val paymentDataRequestJson = JSONObject(googlePayBaseConfiguration.toString()).apply {
        put("allowedPaymentMethods", JSONArray().put(cardPaymentMethod))
        put("transactionInfo", transactionInfo)
        put("merchantInfo", merchantInfo)
    }


    private val readyToPayRequest = IsReadyToPayRequest.fromJson(googlePayBaseConfiguration.toString())




    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.aboutContributors.setHtml(getString(R.string.about_contributors))
        binding.aboutTranslators.setHtml(getString(R.string.about_translators_translatewiki))
        binding.aboutWmf.setHtml(getString(R.string.about_wmf))
        binding.aboutAppLicense.setHtml(getString(R.string.about_app_license))
        binding.activityAboutLibraries.setHtml(getString(R.string.libraries_list))
        binding.aboutVersionText.text = BuildConfig.VERSION_NAME
        binding.aboutLogoImage.setOnClickListener(AboutLogoClickListener())
        binding.aboutContainer.descendants.filterIsInstance<TextView>().forEach {
            it.movementMethod = LinkMovementMethodCompat.getInstance()
        }
        binding.sendFeedbackText.setOnClickListener {
            FeedbackUtil.composeFeedbackEmail(this, "Android App ${BuildConfig.VERSION_NAME} Feedback")
        }



        paymentsClient = createPaymentsClient(this)


        val paymentMethods = JSONArray().put(baseCardPaymentMethod)

        binding.payButton.initialize(ButtonOptions.newBuilder()
            .setButtonTheme(if (WikipediaApp.instance.currentTheme.isDark) ButtonConstants.ButtonTheme.DARK else ButtonConstants.ButtonTheme.LIGHT)
            .setButtonType(ButtonConstants.ButtonType.DONATE)
            .setAllowedPaymentMethods(paymentMethods.toString())
            .build())

        binding.payButton.setOnClickListener {
            val paymentDataRequest =
                PaymentDataRequest.fromJson(paymentDataRequestJson.toString())

            AutoResolveHelper.resolveTask(
                paymentsClient.loadPaymentData(paymentDataRequest),
                this, LOAD_PAYMENT_DATA_REQUEST_CODE)
        }

        val readyToPayTask = paymentsClient.isReadyToPay(readyToPayRequest)
        readyToPayTask.addOnCompleteListener { task ->
            try {
                task.getResult(ApiException::class.java)?.let(::setGooglePayAvailable)
            } catch (exception: ApiException) {
                L.e(exception)
                // Error determining readiness to use Google Pay.
                // Inspect the logs for more details.
            }
        }


    }

    fun createPaymentsClient(activity: Activity): PaymentsClient {
        val walletOptions = Wallet.WalletOptions.Builder()
            .setEnvironment(WalletConstants.ENVIRONMENT_TEST).build()
        return Wallet.getPaymentsClient(activity, walletOptions)
    }

    private fun setGooglePayAvailable(available: Boolean) {
        if (available) {
            binding.payButton.isVisible = true
        } else {
            // Unable to pay using Google Pay. Update your UI accordingly.
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == LOAD_PAYMENT_DATA_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    data?.let { intent ->
                        PaymentData.getFromIntent(intent)?.let { paymentData ->
                            handlePaymentSuccess(paymentData)
                        }
                    }
                }
                Activity.RESULT_CANCELED -> {
                    // The user cancelled the payment attempt
                }
                AutoResolveHelper.RESULT_ERROR -> {
                    AutoResolveHelper.getStatusFromIntent(data)?.let {

                    }
                }
            }
        }
    }

    private fun handlePaymentSuccess(paymentData: PaymentData) {
        val paymentMethodToken = paymentData
            .paymentMethodToken

        // TODO: Use this token to perform a payment through your payment gateway
    }





    private class AboutLogoClickListener : View.OnClickListener {
        private var secretClickCount = 0
        override fun onClick(v: View) {
            ++secretClickCount
            if (secretClickCount == SECRET_CLICK_LIMIT) {
                if (Prefs.isShowDeveloperSettingsEnabled) {
                    FeedbackUtil.showMessage(v.context as Activity, R.string.show_developer_settings_already_enabled)
                } else {
                    Prefs.isShowDeveloperSettingsEnabled = true
                    FeedbackUtil.showMessage(v.context as Activity, R.string.show_developer_settings_enabled)
                }
            }
        }

        companion object {
            private const val SECRET_CLICK_LIMIT = 7
        }
    }


    companion object {
        private const val LOAD_PAYMENT_DATA_REQUEST_CODE = 991
    }
}
