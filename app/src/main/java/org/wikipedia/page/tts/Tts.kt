package org.wikipedia.page.tts

import android.content.ComponentName
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import androidx.core.content.FileProvider
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.MoreExecutors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.wikipedia.WikipediaApp
import org.wikipedia.page.PageTitle
import org.wikipedia.util.ShareUtil
import org.wikipedia.util.StringUtil
import org.wikipedia.util.log.L
import java.io.File
import java.util.Locale

object Tts {

    var textToSpeech: TextToSpeech? = null
    var speechRate = 1f

    var utterances: List<String> = emptyList()
    var currentUtterance = 0

    var audioUrl: String? = null

    var currentPageTitle: PageTitle? = null
        set(value) {
            if (value == null) {
                field = null
            } else {
                val title = PageTitle(value.prefixedText, value.wikiSite)
                title.description = value.description
                title.thumbUrl = value.thumbUrl
                field = title
            }
        }

    private const val SPEAK_FILE_NAME = "audio.wav"
    private var fileToSpeak: File? = null

    var mediaController: MediaController? = null

    fun cleanup() {
        textToSpeech?.stop()
        mediaController?.stop()
        mediaController?.release()
        mediaController = null
        PlaybackService.cleanup()
    }

    fun start(context: Context, pageTitle: PageTitle?, audioUrl: String, utterances: List<String>) {
        currentPageTitle = pageTitle
        this.utterances = utterances
        currentUtterance = 0

        val shareFolder = ShareUtil.getClearShareFolder(context)
        fileToSpeak = File(shareFolder, SPEAK_FILE_NAME)
        if (fileToSpeak?.exists() == true) {
            fileToSpeak?.delete()
        }

        this.audioUrl = audioUrl
        if (audioUrl.isNotEmpty()) {
            speak(context)
            return
        }

        if (textToSpeech == null) {
            textToSpeech = TextToSpeech(context) { status ->
                if (status == TextToSpeech.SUCCESS) {
                    textToSpeech?.setLanguage(Locale.getDefault())
                    generateAndSpeak(context)
                } else {
                    L.d(">>>> Failed to initialize TTS")
                    textToSpeech = null
                }
            }
            speechRate = 1f
        } else {
            generateAndSpeak(context)
        }
    }

    private fun generateAndSpeak(context: Context) {
        if (textToSpeech == null) {
            return
        }


        speak(context)

    }

    private fun speak(context: Context) {
        //if (mediaController?.isConnected == false) {
            cleanup()
        //}

        if (mediaController == null) {
            val sessionToken = SessionToken(context, ComponentName(context, PlaybackService::class.java))
            val controllerFuture = MediaController.Builder(context, sessionToken).buildAsync()
            controllerFuture.addListener(
                {
                    mediaController = controllerFuture.get()
                    // playerView.setPlayer(controller)
                    actuallySpeak(context)
                },
                MoreExecutors.directExecutor()
            )
        } else {
            actuallySpeak(context)
        }
    }

    private fun actuallySpeak(context: Context) {
        val uri = if (audioUrl.isNullOrBlank()) ShareUtil.getUriFromFile(context, fileToSpeak) else Uri.parse(audioUrl)

        val mediaItem = MediaItem.Builder()
            .setMediaId("media-1")
            .setUri(uri)
            .setMediaMetadata(MediaMetadata.Builder()
                .setArtist("Wikipedia")
                .setTitle(StringUtil.fromHtml(currentPageTitle?.displayText.orEmpty()))
                .setArtworkUri(Uri.parse(currentPageTitle?.thumbUrl.orEmpty()))
                .build()
            ).build()

        WikipediaApp.instance.mainThreadHandler.post {
            mediaController?.setMediaItem(mediaItem)
            mediaController?.prepare()
            mediaController?.play()
        }
    }
}