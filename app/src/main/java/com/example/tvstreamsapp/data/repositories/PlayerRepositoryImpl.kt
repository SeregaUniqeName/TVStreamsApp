package com.example.tvstreamsapp.data.repositories

import android.content.Context
import android.net.Uri
import androidx.annotation.OptIn
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.example.tvstreamsapp.data.local.database.ChannelsDao
import com.example.tvstreamsapp.data.remote.HLSConnectionService
import com.example.tvstreamsapp.data.utils.mapDomainToDb
import com.example.tvstreamsapp.domain.PlayerRepository
import com.example.tvstreamsapp.domain.models.TVChannel
import javax.inject.Inject

@UnstableApi
class PlayerRepositoryImpl @OptIn(UnstableApi::class)
@Inject constructor(
    private val context: Context,
    private val connectionService: HLSConnectionService,
    private val channelsDao: ChannelsDao,
) : PlayerRepository {

    private val player: ExoPlayer by lazy {
        ExoPlayer.Builder(context)
            .setMediaSourceFactory(connectionService.createMediaSourceFactory())
            .setVideoScalingMode(C.VIDEO_SCALING_MODE_DEFAULT)
            .build()
    }

    override suspend fun openStream(item: TVChannel) {
        changeActiveItem(item)
        val mediaItem = MediaItem.fromUri(Uri.parse(item.streamUri))
        player.setMediaItem(mediaItem)
        player.prepare()
    }

    override suspend fun changeItemStatus(newItem: TVChannel, oldItem: TVChannel) {
        channelsDao.changeActiveItem(newItem.mapDomainToDb())
        channelsDao.changeActiveItem(oldItem.mapDomainToDb())
        openStream(newItem)
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

    private fun changeActiveItem(item: TVChannel) {
        channelsDao.changeActiveItem(item.mapDomainToDb())
    }
}