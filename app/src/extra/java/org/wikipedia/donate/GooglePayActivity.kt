package org.wikipedia.donate

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.gms.wallet.AutoResolveHelper
import com.google.android.gms.wallet.PaymentData
import com.google.android.gms.wallet.PaymentDataRequest
import com.google.android.gms.wallet.PaymentsClient
import com.google.android.gms.wallet.button.ButtonConstants
import com.google.android.gms.wallet.button.ButtonOptions
import com.google.android.material.chip.Chip
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.wikipedia.R
import org.wikipedia.WikipediaApp
import org.wikipedia.activity.BaseActivity
import org.wikipedia.databinding.ActivityDonateBinding
import org.wikipedia.dataclient.donate.DonationConfig
import org.wikipedia.dataclient.donate.PaymentMethod
import org.wikipedia.util.Resource
import org.wikipedia.util.ResourceUtil
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

class GooglePayActivity : BaseActivity() {
    private lateinit var binding: ActivityDonateBinding
    private lateinit var paymentsClient: PaymentsClient

    private val viewModel: GooglePayViewModel by viewModels()
    private val decimalFormat = DecimalFormat("0")

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDonateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = ""

        paymentsClient = GooglePayComponent.createPaymentsClient(this)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch {
                    viewModel.uiState.collect { resource ->
                        when (resource) {
                            is Resource.Loading -> {
                                setLoadingState()
                            }
                            is Resource.Error -> {
                                setErrorState(resource.throwable)
                            }
                            is Resource.Success -> {
                                onContentsReceived(resource.data.first, resource.data.second)
                            }
                        }
                    }
                }
            }
        }

        binding.errorView.backClickListener = View.OnClickListener {
            onBackPressed()
        }

        binding.payButton.setOnClickListener {
            val paymentDataRequest = PaymentDataRequest.fromJson(GooglePayComponent.getPaymentDataRequestJson().toString())
            AutoResolveHelper.resolveTask(
                paymentsClient.loadPaymentData(paymentDataRequest),
                this, LOAD_PAYMENT_DATA_REQUEST_CODE
            )
        }
    }

    private fun setLoadingState() {
        binding.contentsContainer.isVisible = false
        binding.errorView.isVisible = false
        binding.progressBar.isVisible = true
    }

    private fun setErrorState(throwable: Throwable) {
        binding.contentsContainer.isVisible = false
        binding.progressBar.isVisible = false
        binding.errorView.isVisible = true
        binding.errorView.setError(throwable)
    }

    private fun onContentsReceived(paymentMethods: List<PaymentMethod>, donationConfig: DonationConfig) {
        binding.contentsContainer.isVisible = true
        binding.progressBar.isVisible = false
        binding.errorView.isVisible = false

        val methods = JSONArray().put(GooglePayComponent.baseCardPaymentMethod)
        binding.payButton.initialize(ButtonOptions.newBuilder()
            .setButtonTheme(if (WikipediaApp.instance.currentTheme.isDark) ButtonConstants.ButtonTheme.DARK else ButtonConstants.ButtonTheme.LIGHT)
            .setButtonType(ButtonConstants.ButtonType.DONATE)
            .setAllowedPaymentMethods(methods.toString())
            .build())

        // TODO: is this right?
        val format = NumberFormat.getCurrencyInstance(Locale.getDefault())
        format.maximumFractionDigits = 0

        val presets = donationConfig.currencyAmountPresets[format.currency!!.currencyCode]
        presets?.forEach { amount ->
            val chip = Chip(this)
            chip.setTextAppearanceResource(R.style.H2)
            chip.text = format.format(amount)
            chip.isCheckable = true
            chip.setOnClickListener {
                (it as Chip).setChipBackgroundColorResource(ResourceUtil.getThemedAttributeId(this, R.attr.progressive_color))
            }

            binding.amountPresetsGroup.addView(chip)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == LOAD_PAYMENT_DATA_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    data?.let { intent ->
                        PaymentData.getFromIntent(intent)?.let { paymentData ->
                            // TODO: handle payment data
                        }
                    }
                }
                Activity.RESULT_CANCELED -> {
                    // The user cancelled the payment attempt
                }
                AutoResolveHelper.RESULT_ERROR -> {
                    AutoResolveHelper.getStatusFromIntent(data)?.let {
                        // TODO: handle error
                    }
                }
            }
        }
    }

    companion object {
        private const val LOAD_PAYMENT_DATA_REQUEST_CODE = 42

        fun newIntent(context: Context): Intent {
            return Intent(context, GooglePayActivity::class.java)
        }
    }
}
