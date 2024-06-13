package org.wikipedia.page.tts

import android.content.Context
import android.content.Intent
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
import org.wikipedia.R
import org.wikipedia.databinding.ViewNarrationPopupBinding
import org.wikipedia.page.PageTitle
import org.wikipedia.util.DimenUtil
import org.wikipedia.util.StringUtil
import org.wikipedia.views.ViewUtil

class NarrationPopupView(context: Context) : FrameLayout(context) {

    private var binding = ViewNarrationPopupBinding.inflate(LayoutInflater.from(context), this, true)
    private var popupWindowHost: PopupWindow? = null

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
        updateSpeedButtons()
        updatePlayPauseButton()

        binding.seekBackButton.setOnClickListener {
        }

        binding.seekForwardButton.setOnClickListener {
        }

        binding.playPauseButton.setOnClickListener {
            PlaybackService.currentSession?.let {
                if (it.player.isPlaying) {
                    it.player.pause()
                } else {
                    it.player.play()
                }
            }
            updatePlayPauseButton()
        }

        binding.decreaseSpeedButton.setOnClickListener {
            Tts.speechRate -= 0.1f
            Tts.textToSpeech?.setSpeechRate(Tts.speechRate)
            updateSpeedButtons()
        }

        binding.increaseSpeedButton.setOnClickListener {
            Tts.speechRate += 0.1f
            Tts.textToSpeech?.setSpeechRate(Tts.speechRate)
            updateSpeedButtons()
        }

        binding.stopButton.setOnClickListener {

            context.stopService(Intent(context, PlaybackService::class.java))

            //PlaybackService.currentSession?.player?.stop()
            //PlaybackService.currentSession?.release()
            Tts.cleanup()

            dismissPopupWindowHost()
        }
    }

    private fun dismissPopupWindowHost() {
        popupWindowHost?.let {
            it.dismiss()
            popupWindowHost = null
        }
    }

    private fun updatePlayPauseButton() {
        PlaybackService.currentSession?.let {
            binding.playPauseButton.setImageResource(if (it.player.isPlaying) R.drawable.ic_pause_black_24dp else R.drawable.ic_play_arrow_black_24dp)
        }
    }

    private fun updateSpeedButtons() {
        binding.decreaseSpeedButton.isEnabled = Tts.speechRate > 0.1f
        binding.increaseSpeedButton.isEnabled = Tts.speechRate < 2.0f
        binding.speedText.text = String.format("%.1fx", Tts.speechRate)
    }
}
