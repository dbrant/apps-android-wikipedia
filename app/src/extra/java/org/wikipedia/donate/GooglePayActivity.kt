package org.wikipedia.donate

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.gms.wallet.AutoResolveHelper
import com.google.android.gms.wallet.PaymentData
import com.google.android.gms.wallet.PaymentDataRequest
import com.google.android.gms.wallet.PaymentsClient
import com.google.android.gms.wallet.button.ButtonConstants
import com.google.android.gms.wallet.button.ButtonOptions
import com.google.android.material.button.MaterialButton
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
import java.util.Locale

class GooglePayActivity : BaseActivity() {
    private lateinit var binding: ActivityDonateBinding
    private lateinit var paymentsClient: PaymentsClient
    private lateinit var currencyFormat: NumberFormat

    private val viewModel: GooglePayViewModel by viewModels()

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDonateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = ""

        // TODO: is this right?
        currencyFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())
        currencyFormat.maximumFractionDigits = 0

        binding.donateAmountInput.prefixText = currencyFormat.currency?.symbol ?: ""

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

        binding.donateAmountText.addTextChangedListener { text ->
            val amount = text.toString().toDoubleOrNull()
            if (amount != null) {
                val min = viewModel.donationConfig?.currencyMinimumDonation?.get(currencyFormat.currency!!.currencyCode) ?: 0f
                val max = viewModel.donationConfig?.currencyMaximumDonation?.get(currencyFormat.currency!!.currencyCode) ?: 0f

                if (amount < min) {
                    binding.donateAmountInput.error = "Please select an amount (Minimum " + currencyFormat.format(amount) + ")." //getString(R.string.donate_amount_error, currencyFormat.format(min))
                } else if (amount > max) {
                    binding.donateAmountInput.error = "We cannot accept donations greater than  " + currencyFormat.format(amount) + " through our app. Please contact our major gifts staff at ......." //getString(R.string.donate_amount_error, currencyFormat.format(min))
                } else {
                    binding.donateAmountInput.error = null
                }
            }
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

        val viewIds = mutableListOf<Int>()
        val presets = donationConfig.currencyAmountPresets[currencyFormat.currency!!.currencyCode]
        presets?.forEach { amount ->
            val viewId = View.generateViewId()
            viewIds.add(viewId)
            val button = MaterialButton(this)
            button.text = currencyFormat.format(amount)
            button.id = viewId
            binding.amountPresetsContainer.addView(button)
            button.setOnClickListener {
                setButtonHighlighted(it)
                binding.donateAmountText.setText(currencyFormat.format(amount))
            }
        }
        binding.amountPresetsFlow.referencedIds = viewIds.toIntArray()
        setButtonHighlighted()
    }

    private fun setButtonHighlighted(button: View? = null) {
        binding.amountPresetsContainer.children.forEach { child ->
            if (child is MaterialButton) {
                if (child == button) {
                    child.backgroundTintList = ResourceUtil.getThemedColorStateList(this, R.attr.progressive_color)
                    child.setTextColor(Color.WHITE)
                } else {
                    child.backgroundTintList = ResourceUtil.getThemedColorStateList(this, R.attr.background_color)
                    child.setTextColor(ResourceUtil.getThemedColor(this, R.attr.primary_color))
                }
            }
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
