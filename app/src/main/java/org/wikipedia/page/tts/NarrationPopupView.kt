package org.wikipedia.page.tts

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.PopupWindow
import androidx.core.view.doOnDetach
import androidx.core.widget.PopupWindowCompat
import org.wikipedia.databinding.ViewNarrationPopupBinding
import org.wikipedia.util.DimenUtil

class NarrationPopupView(context: Context) : FrameLayout(context) {

    private var binding = ViewNarrationPopupBinding.inflate(LayoutInflater.from(context), this, true)
    private var popupWindowHost: PopupWindow? = null

    init {
    }

    fun show(anchorView: View) {
        popupWindowHost = PopupWindow(this, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true)
        popupWindowHost?.let {
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            PopupWindowCompat.setOverlapAnchor(it, true)
            it.showAsDropDown(anchorView, 0, anchorView.height - DimenUtil.roundedDpToPx(48f*3), Gravity.END or Gravity.BOTTOM)
        }

        anchorView.doOnDetach {
            dismissPopupWindowHost()
        }

        binding.seekBackButton.setOnClickListener {
        }

        binding.seekForwardButton.setOnClickListener {
        }

        binding.playPauseButton.setOnClickListener {
        }

        binding.decreaseSpeedButton.setOnClickListener {
        }

        binding.increaseSpeedButton.setOnClickListener {
        }

        binding.stopButton.setOnClickListener {
            dismissPopupWindowHost()
        }
    }

    private fun dismissPopupWindowHost() {
        popupWindowHost?.let {
            it.dismiss()
            popupWindowHost = null
        }
    }
}
