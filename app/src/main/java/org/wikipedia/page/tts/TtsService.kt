package org.wikipedia.page.tts

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import android.speech.tts.UtteranceProgressListener
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
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
    private var currentSession: MediaSession? = null

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
        L.d(">>>> PlaybackService onCreate")
        super.onCreate()

        val player = if (Tts.audioUrl.isNullOrEmpty())
            TtsPlayer(Looper.getMainLooper(), this, Tts.textToSpeech!!)
        else ExoPlayer.Builder(this).build()

        currentSession = MediaSession.Builder(this, player)
            .setCallback(object : MediaSession.Callback {

                @OptIn(UnstableApi::class)
                override fun onConnect(
                    session: MediaSession,
                    controller: MediaSession.ControllerInfo
                ): ConnectionResult {
                    L.d(">>>> MediaSession onConnect")
                    isRunning = true

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
                            .setAvailablePlayerCommands(playerCommands)
                            .setAvailableSessionCommands(sessionCommands)
                            .build()
                    }
                    // Default commands with default custom layout for all other controllers.
                    return ConnectionResult.AcceptedResultBuilder(session).build()
                }

                @OptIn(UnstableApi::class)
                override fun onCustomCommand(
                    session: MediaSession,
                    controller: MediaSession.ControllerInfo,
                    customCommand: SessionCommand,
                    args: Bundle
                ): ListenableFuture<SessionResult> {
                    L.d(">>>> MediaSession onCustomCommand: ${customCommand.customAction}")
                    if (customCommand.customAction == CUSTOM_COMMAND_REWIND_SEC) {
                        session.player.seekTo(player.currentPosition - 10_000)
                        return Futures.immediateFuture(SessionResult(SessionResult.RESULT_SUCCESS))
                    } else if (customCommand.customAction == CUSTOM_COMMAND_FORWARD_SEC) {
                        session.player.seekTo(player.currentPosition + 10_000)
                        return Futures.immediateFuture(SessionResult(SessionResult.RESULT_SUCCESS))
                    }
                    return super.onCustomCommand(session, controller, customCommand, args)
                }

                override fun onDisconnected(
                    session: MediaSession,
                    controller: MediaSession.ControllerInfo
                ) {
                    L.d(">>>> MediaSession onDisconnected")
                    isRunning = false
                    super.onDisconnected(session, controller)
                }
            })
            .build()
    }

    override fun onDestroy() {
        L.d(">>>> PlaybackService onDestroy")
        currentSession?.run {
            player.stop()
            player.release()
            release()
            currentSession = null
        }
        isRunning = false
        super.onDestroy()
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        L.d(">>>> PlaybackService onTaskRemoved")
        val player = currentSession?.player
        if (player != null && (!player.playWhenReady
            || player.mediaItemCount == 0
            || player.playbackState == Player.STATE_ENDED)) {
            // Stop the service if not playing, continue playing in the background
            // otherwise.
            stopSelf()
        }
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? {
        L.d(">>>> PlaybackService onGetSession")
        return currentSession
    }

    companion object {
        var isRunning = false

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
                    Player.COMMAND_GET_METADATA,
                    Player.COMMAND_GET_CURRENT_MEDIA_ITEM,
                    Player.COMMAND_GET_MEDIA_ITEMS_METADATA,
                    Player.COMMAND_CHANGE_MEDIA_ITEMS,
                    Player.COMMAND_PREPARE,
                    Player.COMMAND_SET_MEDIA_ITEM,
                ).build()
            )
            //.setPlayWhenReady(true, PLAY_WHEN_READY_CHANGE_REASON_USER_REQUEST)
            .setPlaybackState(STATE_IDLE)
            //.setAudioAttributes(AudioAttributes.DEFAULT)
            //.setPlaylist(listOf(MediaItemData.Builder("test").build()))
            //.setPlaylistMetadata(
            //    MediaMetadata.Builder().setMediaType(MediaMetadata.MEDIA_TYPE_PLAYLIST)
            ////        .setTitle("TTS test")
            //        .build()
            //)
            .build()

        init {
            textToSpeech.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String) {
                    L.d(">>>> TTS onStart")
                    //updatePlaybackState(STATE_READY, false)
                }

                override fun onDone(utteranceId: String) {
                    L.d(">>>> TTS onDone")

                    Tts.currentUtterance++
                    speakNextUtterance()

                    //updatePlaybackState(STATE_ENDED, false)
                }

                override fun onError(utteranceId: String) {
                    L.d(">>>> TTS onError")
                    updatePlaybackState(STATE_ENDED)
                }
            })
        }

        override fun getState(): State {
            return state
        }

        override fun handleAddMediaItems(
            index: Int,
            mediaItems: MutableList<MediaItem>
        ): ListenableFuture<*> {
            L.d(">>>> handleAddMediaItems")
            return Futures.immediateVoidFuture()
        }

        override fun handleSetMediaItems(
            mediaItems: MutableList<MediaItem>,
            startIndex: Int,
            startPositionMs: Long
        ): ListenableFuture<*> {
            L.d(">>>> handleSetMediaItems")
            Handler(Looper.getMainLooper()).post {
                state = state.buildUpon()
                    .setPlaylist(listOf(MediaItemData
                        .Builder("test")
                        .setMediaItem(mediaItems[0])
                        .setIsSeekable(true)
                        .setDurationUs(10000000L)
                        .build()))
                    .setIsLoading(false)
                    .setContentPositionMs(2000)
                    .build()
                invalidateState()
            }
            return Futures.immediateVoidFuture()
        }

        override fun handleSetPlaylistMetadata(playlistMetadata: MediaMetadata): ListenableFuture<*> {
            L.d(">>>> handleSetPlaylistMetadata")
            return Futures.immediateVoidFuture()
        }

        private fun updatePlaybackState(playbackState: Int, playWhenReady: Boolean = true) {
            L.d(">>>> updatePlaybackState: $playbackState")

            val mainHandler = Handler(Looper.getMainLooper())
            mainHandler.post {
                state = state.buildUpon()
                    .setPlaybackState(playbackState)
                    .setPlayWhenReady(playWhenReady, PLAY_WHEN_READY_CHANGE_REASON_USER_REQUEST)
                    .build()
                invalidateState()
            }
        }

        override fun handlePrepare(): ListenableFuture<*> {
            L.d(">>>> handlePrepare")

            if (playWhenReady) {
                speakNextUtterance()
                updatePlaybackState(STATE_READY, true)
            } else {
                textToSpeech.stop()
                updatePlaybackState(STATE_READY, false)
            }

            return Futures.immediateVoidFuture()
        }

        override fun handleSetPlayWhenReady(playWhenReady: Boolean): ListenableFuture<*> {
            L.d(">>>> handleSetPlayWhenReady: $playWhenReady")

            if (playWhenReady) {
                if (!textToSpeech.isSpeaking) {
                    speakNextUtterance()
                }
                updatePlaybackState(STATE_READY, true)
            } else {
                if (textToSpeech.isSpeaking) {
                    textToSpeech.stop()
                }
                updatePlaybackState(STATE_READY, false)
            }

            return Futures.immediateVoidFuture()
        }

        override fun handleRelease(): ListenableFuture<*> {
            L.d(">>>> handleRelease")
            textToSpeech.stop()
            textToSpeech.shutdown()
            return Futures.immediateVoidFuture()
        }

        override fun handleStop(): ListenableFuture<*> {
            L.d(">>>> handleStop")
            textToSpeech.stop()
            return Futures.immediateVoidFuture()
        }

        override fun handleSetShuffleModeEnabled(shuffleModeEnabled: Boolean): ListenableFuture<*> {
            L.d(">>>> handleSetShuffleModeEnabled")
            return Futures.immediateVoidFuture()
        }

        override fun onInit(status: Int) {
            L.i("Tts init")
        }


        private fun speakNextUtterance() {
            val text = Tts.utterances.getOrNull(Tts.currentUtterance).orEmpty()
            if (text.isEmpty()) {
                updatePlaybackState(STATE_ENDED)
                Tts.currentUtterance = 0
                return
            }

            textToSpeech.speak(
                text,
                TextToSpeech.QUEUE_FLUSH,
                null,
                TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID
            )
        }

    }
}
