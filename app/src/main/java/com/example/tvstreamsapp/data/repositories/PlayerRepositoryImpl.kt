package com.example.tvstreamsapp.data.repositories

import android.content.Context
import android.net.Uri
import androidx.annotation.OptIn
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import com.example.tvstreamsapp.domain.PlayerRepository
import com.example.tvstreamsapp.domain.models.TVChannel
import javax.inject.Inject

@UnstableApi
class PlayerRepositoryImpl @OptIn(UnstableApi::class)
@Inject constructor(
    private val context: Context,
    private val connectionService: DefaultMediaSourceFactory,
) : PlayerRepository {

    private val player: ExoPlayer by lazy {
        ExoPlayer.Builder(context)
            .setMediaSourceFactory(connectionService)
            .setVideoScalingMode(C.VIDEO_SCALING_MODE_DEFAULT)
            .build()
    }

    override suspend fun openStream(item: TVChannel) {
        preparePlayer(item)
        player.play()
    }

    override fun release() {
        returnPlayer().release()
    }

    override fun returnPlayer(): ExoPlayer {
        return player
    }

    private fun preparePlayer(item: TVChannel) {
        val mediaItem = MediaItem.fromUri(Uri.parse(item.streamUri))
        returnPlayer().setMediaItem(mediaItem)
        returnPlayer().prepare()
    }
}