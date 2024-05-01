package org.wikipedia.page.tts

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import android.speech.tts.UtteranceProgressListener
import androidx.annotation.OptIn
import androidx.media3.common.AudioAttributes
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.common.Player.Commands
import androidx.media3.common.SimpleBasePlayer
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.CommandButton
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSession.ConnectionResult
import androidx.media3.session.MediaSessionService
import androidx.media3.session.SessionCommand
import androidx.media3.session.SessionResult
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture
import org.wikipedia.R
import org.wikipedia.util.log.L


class PlaybackService : MediaSessionService() {
    private var mediaSession: MediaSession? = null

    private val customLayoutCommandButtons: List<CommandButton> =
        listOf(
            CommandButton.Builder()
                .setDisplayName("Rewind")
                .setEnabled(true)
                .setIconResId(R.drawable.ic_replay_10)
                .setSessionCommand(SessionCommand(CUSTOM_COMMAND_REWIND_SEC, Bundle.EMPTY))
                .build(),
            CommandButton.Builder()
                .setDisplayName("Forward")
                .setEnabled(true)
                .setIconResId(R.drawable.ic_forward_10)
                .setSessionCommand(SessionCommand(CUSTOM_COMMAND_FORWARD_SEC, Bundle.EMPTY))
                .build(),
        )



    override fun onCreate() {
        super.onCreate()

        val player = TtsPlayer(Looper.getMainLooper(), this, Tts.textToSpeech!!)

            // ExoPlayer.Builder(this).build()

        mediaSession = MediaSession.Builder(this, player)
            .setCallback(object : MediaSession.Callback {

                @OptIn(UnstableApi::class)
                override fun onConnect(
                    session: MediaSession,
                    controller: MediaSession.ControllerInfo
                ): ConnectionResult {
                    if (session.isMediaNotificationController(controller)) {
                        val sessionCommands =
                            ConnectionResult.DEFAULT_SESSION_COMMANDS.buildUpon()
                                .also { builder ->
                                    customLayoutCommandButtons.forEach { commandButton ->
                                        commandButton.sessionCommand?.let { builder.add(it) }
                                    }
                                }
                                .build()
                        val playerCommands =
                            ConnectionResult.DEFAULT_PLAYER_COMMANDS.buildUpon()
                                //.remove(COMMAND_SEEK_TO_PREVIOUS)
                                //.remove(COMMAND_SEEK_TO_PREVIOUS_MEDIA_ITEM)
                                //.remove(COMMAND_SEEK_TO_NEXT)
                                //.remove(COMMAND_SEEK_TO_NEXT_MEDIA_ITEM)
                                .build()

                        // Custom layout and available commands to configure the legacy/framework session.
                        return ConnectionResult.AcceptedResultBuilder(session)
                            .setCustomLayout(customLayoutCommandButtons)
                            //.setAvailablePlayerCommands(playerCommands)
                            .setAvailableSessionCommands(sessionCommands)
                            .build()
                    }
                    // Default commands with default custom layout for all other controllers.
                    return ConnectionResult.AcceptedResultBuilder(session).build()
                }

                override fun onCustomCommand(
                    session: MediaSession,
                    controller: MediaSession.ControllerInfo,
                    customCommand: SessionCommand,
                    args: Bundle
                ): ListenableFuture<SessionResult> {
                    if (customCommand.customAction == CUSTOM_COMMAND_REWIND_SEC) {
                        session.player.seekTo(player.currentPosition - 10_000)
                        return Futures.immediateFuture(SessionResult(SessionResult.RESULT_SUCCESS))
                    } else if (customCommand.customAction == CUSTOM_COMMAND_FORWARD_SEC) {
                        session.player.seekTo(player.currentPosition + 10_000)
                        return Futures.immediateFuture(SessionResult(SessionResult.RESULT_SUCCESS))
                    }
                    return super.onCustomCommand(session, controller, customCommand, args)
                }
            })
            .build()
    }

    override fun onDestroy() {
        mediaSession?.run {
            player.release()
            release()
            mediaSession = null
        }
        super.onDestroy()
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        val player = mediaSession?.player
        if (player != null && (!player.playWhenReady
            || player.mediaItemCount == 0
            || player.playbackState == Player.STATE_ENDED)) {
            // Stop the service if not playing, continue playing in the background
            // otherwise.
            stopSelf()
        }
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? {
        return mediaSession
    }

    companion object {
        private val CUSTOM_COMMAND_REWIND_SEC = "CUSTOM_COMMAND_REWIND_SEC"
        private val CUSTOM_COMMAND_FORWARD_SEC = "CUSTOM_COMMAND_FORWARD_SEC"
    }

    @OptIn(UnstableApi::class)
    class TtsPlayer(looper: Looper, context: Context, val textToSpeech: TextToSpeech) : SimpleBasePlayer(looper), OnInitListener {

        private var state = State.Builder()
            .setAvailableCommands(
                Commands.Builder().addAll(
                    COMMAND_PLAY_PAUSE,
                    COMMAND_SEEK_BACK,
                    COMMAND_SEEK_FORWARD,
                    COMMAND_SET_SHUFFLE_MODE,
                    Player.COMMAND_GET_CURRENT_MEDIA_ITEM,
                    Player.COMMAND_GET_MEDIA_ITEMS_METADATA
                ).build()
            )
            .setPlayWhenReady(false, PLAY_WHEN_READY_CHANGE_REASON_USER_REQUEST)
            .setAudioAttributes(AudioAttributes.DEFAULT)
            .setPlaylist(listOf(MediaItemData.Builder("test").build()))
            .setPlaylistMetadata(
                MediaMetadata.Builder().setMediaType(MediaMetadata.MEDIA_TYPE_PLAYLIST)
                    .setTitle("TTS test")
                    .build()
            )
            .setCurrentMediaItemIndex(0)
            .build()

        /**
         * Creates the [TextToSpeech] wrapper.
         *
         * @param looper The [Looper] used to call all methods on.
         */
        init {
            textToSpeech.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String) {
                    L.i("onStart")
                    updatePlaybackState(STATE_READY, true)
                }

                override fun onDone(utteranceId: String) {
                    L.i("onDone")
                    updatePlaybackState(STATE_ENDED, false)
                }

                override fun onError(utteranceId: String) {
                    L.i("onError")
                    updatePlaybackState(STATE_ENDED, false)
                }
            })
        }

        override fun getState(): State {
            return state
        }

        private fun updatePlaybackState(playbackState: Int, playWhenReady: Boolean) {
            val mainHandler = Handler(Looper.getMainLooper())
            mainHandler.post {
                state = state.buildUpon()
                    .setPlaybackState(playbackState)
                    .setPlayWhenReady(playWhenReady, PLAY_WHEN_READY_CHANGE_REASON_USER_REQUEST)
                    .build()
                invalidateState()
            }
        }

        override fun handleSetPlayWhenReady(playWhenReady: Boolean): ListenableFuture<*> {
            L.i("handleSetPlayWhenReady: $playWhenReady")
            if (playWhenReady) {
                textToSpeech.speak(
                    "Hello World, this is a sample text for testing Media3's SimpleBasePlayer. I expect background plyback to work and a notification to show up. However none of that is working. Hello World, this is a sample text for testing Media3's SimpleBasePlayer. I expect background plyback to work and a notification to show up. However none of that is working. Hello World, this is a sample text for testing Media3's SimpleBasePlayer. I expect background plyback to work and a notification to show up. However none of that is working.",
                    TextToSpeech.QUEUE_FLUSH,
                    null,
                    TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID
                )
            } else {
                textToSpeech.stop()
            }
            return Futures.immediateVoidFuture()
        }

        override fun handleRelease(): ListenableFuture<*> {
            textToSpeech.stop()
            textToSpeech.shutdown()
            return Futures.immediateVoidFuture()
        }

        override fun handleStop(): ListenableFuture<*> {
            textToSpeech.stop()
            return Futures.immediateVoidFuture()
        }

        override fun handleSetShuffleModeEnabled(shuffleModeEnabled: Boolean): ListenableFuture<*> {
            return Futures.immediateVoidFuture()
        }

        override fun onInit(status: Int) {
            L.i("Tts init")
        }
    }


}
