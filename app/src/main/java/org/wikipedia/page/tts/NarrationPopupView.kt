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
import androidx.core.view.isVisible
import androidx.core.widget.PopupWindowCompat
import org.wikipedia.databinding.ViewNarrationPopupBinding
import org.wikipedia.page.PageTitle
import org.wikipedia.util.DimenUtil
import org.wikipedia.util.StringUtil
import org.wikipedia.views.ViewUtil

class NarrationPopupView(context: Context) : FrameLayout(context) {

    private var binding = ViewNarrationPopupBinding.inflate(LayoutInflater.from(context), this, true)
    private var popupWindowHost: PopupWindow? = null

    init {
    }

    fun show(anchorView: View, pageTitle: PageTitle) {
        popupWindowHost = PopupWindow(this, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true)
        popupWindowHost?.let {
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            PopupWindowCompat.setOverlapAnchor(it, true)
            it.showAsDropDown(anchorView, 0, anchorView.height - DimenUtil.roundedDpToPx(48f*4), Gravity.END or Gravity.BOTTOM)
        }

        anchorView.doOnDetach {
            dismissPopupWindowHost()
        }

        binding.articleTitle.text = StringUtil.fromHtml(pageTitle.displayText)
        if (pageTitle.thumbUrl.isNullOrEmpty()) {
            binding.articleThumbnail.isVisible = false
        } else {
            binding.articleThumbnail.isVisible = true
            ViewUtil.loadImage(binding.articleThumbnail, pageTitle.thumbUrl)
        }

        binding.seekBackButton.setOnClickListener {
        }

        binding.seekForwardButton.setOnClickListener {
        }

        binding.playPauseButton.setOnClickListener {
        }

        binding.decreaseSpeedButton.setOnClickListener {
            Tts.textToSpeech?.setSpeechRate(Tts.speechRate - 0.1f)
        }

        binding.increaseSpeedButton.setOnClickListener {
            Tts.textToSpeech?.setSpeechRate(Tts.speechRate + 0.1f)
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
