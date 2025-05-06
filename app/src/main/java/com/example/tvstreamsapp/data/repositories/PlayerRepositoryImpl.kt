package com.example.tvstreamsapp.data.repositories

import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import com.example.tvstreamsapp.domain.PlayerRepository
import com.example.tvstreamsapp.domain.models.TVChannel
import javax.inject.Inject

class PlayerRepositoryImpl @Inject constructor(
    private val connectionService: DefaultMediaSourceFactory,
) : PlayerRepository {

    private lateinit var cachedChannel: TVChannel

    override fun setChannel(item: TVChannel) {
        cachedChannel = item
    }

    override fun provideMediaSource(): DefaultMediaSourceFactory {
        return connectionService
    }

    override fun getActiveChannel(): TVChannel {
        return cachedChannel
    }

}