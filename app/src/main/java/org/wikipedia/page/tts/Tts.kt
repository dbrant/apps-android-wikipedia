package org.wikipedia.page.tts

import android.content.ComponentName
import android.content.Context
import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.MoreExecutors

object Tts {
    fun start(context: Context) {
        val sessionToken = SessionToken(context, ComponentName(context, PlaybackService::class.java))
        val controllerFuture = MediaController.Builder(context, sessionToken).buildAsync()
        controllerFuture.addListener(
            {
                val controller = controllerFuture.get()
                //playerView.setPlayer(controller)

                val mediaItem = MediaItem.Builder()
                    .setMediaId("media-1")
                    .setUri(Uri.parse("https://upload.wikimedia.org/wikipedia/commons/8/8e/En-Xenu.ogg"))
                    .setMediaMetadata(MediaMetadata.Builder()
                        .setArtist("Dmitry Brant")
                        .setTitle("Big Bang")
                        .setArtworkUri(Uri.parse("https://upload.wikimedia.org/wikipedia/commons/thumb/6/6f/CMB_Timeline300_no_WMAP.jpg/640px-CMB_Timeline300_no_WMAP.jpg"))
                        .build()
                    ).build()

                controller.setMediaItem(mediaItem)
                controller.prepare()
                controller.play()
            },
            MoreExecutors.directExecutor()
        )
    }
}