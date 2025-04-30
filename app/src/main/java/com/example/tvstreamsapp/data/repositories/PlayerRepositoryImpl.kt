package com.example.tvstreamsapp.data.repositories

import android.content.Context
import android.net.Uri
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.example.tvstreamsapp.data.remote.HLSConnectionService
import com.example.tvstreamsapp.domain.PlayerRepository
import javax.inject.Inject

@UnstableApi
class PlayerRepositoryImpl @OptIn(UnstableApi::class)
@Inject constructor(
    private val context: Context,
    private val connectionService: HLSConnectionService,
) : PlayerRepository {

    private val player: ExoPlayer by lazy {
        ExoPlayer.Builder(context)
            .setMediaSourceFactory(connectionService.createMediaSourceFactory())
            .build()
    }

    override suspend fun openStream(streamUri: String) {
        val mediaItem = MediaItem.fromUri(Uri.parse(streamUri))
        player.setMediaItem(mediaItem)
        player.prepare()
    }

    override fun play() {
        player.play()
    }

    override fun pause() {
        player.pause()
    }

    override fun release() {
        player.release()
    }

    override fun getPlayer(): ExoPlayer {
        return player
    }
}