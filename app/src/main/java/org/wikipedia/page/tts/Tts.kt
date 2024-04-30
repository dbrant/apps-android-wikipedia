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
import org.wikipedia.util.ShareUtil
import org.wikipedia.util.log.L
import java.io.File
import java.util.Locale

object Tts {

    var textToSpeech: TextToSpeech? = null
    var fileToSpeak: File? = null

    var mediaController: MediaController? = null

    fun start(context: Context, text: String) {
        val shareFolder = ShareUtil.getClearShareFolder(context)
        shareFolder?.mkdirs()
        fileToSpeak = File(shareFolder, "foo.mp3")


        if (textToSpeech == null) {
            textToSpeech = TextToSpeech(context) { status ->
                if (status == TextToSpeech.SUCCESS) {
                    textToSpeech?.setLanguage(Locale.getDefault())
                    generateAndSpeak(context, text)
                } else {
                    L.d("Failed to initialize TTS")
                    textToSpeech = null
                }
            }

            textToSpeech?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {
                    L.d("Utterance started: $utteranceId")
                }

                override fun onDone(utteranceId: String?) {
                    L.d("Utterance done: $utteranceId")
                    speak(context)
                }

                @Deprecated("Deprecated in Java")
                override fun onError(utteranceId: String?) {
                    // TODO
                }

                override fun onError(utteranceId: String?, errorCode: Int) {
                    L.d("Utterance error: $utteranceId, $errorCode")
                }
            })
        } else {
            generateAndSpeak(context, text)
        }
    }

    private fun generateAndSpeak(context: Context, text: String) {
        if (textToSpeech == null) {
            return
        }

        val params = Bundle()
        params.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "uttId1");
        textToSpeech?.synthesizeToFile(text, params, fileToSpeak, "uttId1")
    }

    private fun speak(context: Context) {
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
        val mediaItem = MediaItem.Builder()
            .setMediaId("media-1")
            .setUri(ShareUtil.getUriFromFile(context, fileToSpeak))
            //.setUri(Uri.parse("https://upload.wikimedia.org/wikipedia/commons/8/8e/En-Xenu.ogg"))
            .setMediaMetadata(MediaMetadata.Builder()
                .setArtist("Dmitry Brant")
                .setTitle("Big Bang")
                .setArtworkUri(Uri.parse("https://upload.wikimedia.org/wikipedia/commons/thumb/6/6f/CMB_Timeline300_no_WMAP.jpg/640px-CMB_Timeline300_no_WMAP.jpg"))
                .build()
            ).build()

        WikipediaApp.instance.mainThreadHandler.post {
            mediaController?.setMediaItem(mediaItem)
            mediaController?.prepare()
            mediaController?.play()
        }
    }
}