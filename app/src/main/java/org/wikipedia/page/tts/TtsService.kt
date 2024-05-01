package org.wikipedia.page.tts

import android.content.Intent
import android.os.Bundle
import androidx.annotation.OptIn
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.CommandButton
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSession.ConnectionResult
import androidx.media3.session.MediaSessionService
import androidx.media3.session.SessionCommand
import androidx.media3.session.SessionResult
import com.google.common.collect.ImmutableList
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture
import org.wikipedia.R

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
        val player = ExoPlayer.Builder(this).build()
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
}
