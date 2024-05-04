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

    var utterances: List<String> = emptyList()
    var currentUtterance = 0

    var audioUrl: String? = null

    private var currentPageTitle: PageTitle? = null

    private const val SPEAK_FILE_NAME = "audio.wav"
    private var fileToSpeak: File? = null

    var mediaController: MediaController? = null

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
                    L.d("Failed to initialize TTS")
                    textToSpeech = null
                }
            }

            /*
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
                    L.d("Utterance error: $utteranceId")
                }

                override fun onError(utteranceId: String?, errorCode: Int) {
                    L.d("Utterance error: $utteranceId, $errorCode")
                }
            })
             */
        } else {
            generateAndSpeak(context)
        }
    }

    private fun generateAndSpeak(context: Context) {
        if (textToSpeech == null) {
            return
        }


        speak(context)


        //val params = Bundle()
        //params.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "uttId1");
        //textToSpeech?.synthesizeToFile(text, params, fileToSpeak, "uttId1")
    }

    private fun speak(context: Context) {
        //if (mediaController?.isConnected == false) {
            mediaController?.release()
            mediaController = null
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
                .setArtworkUri(Uri.parse(currentPageTitle?.thumbUrl.orEmpty()))  //"https://upload.wikimedia.org/wikipedia/commons/thumb/6/6f/CMB_Timeline300_no_WMAP.jpg/640px-CMB_Timeline300_no_WMAP.jpg"))
                .build()
            ).build()

        WikipediaApp.instance.mainThreadHandler.post {
            mediaController?.setMediaItem(mediaItem)
            mediaController?.prepare()
            mediaController?.play()
        }
    }
}