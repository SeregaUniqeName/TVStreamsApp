package com.example.tvstreamsapp.domain

import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import com.example.tvstreamsapp.domain.models.TVChannel

interface PlayerRepository {

    fun setChannel(item: TVChannel)
    fun provideMediaSource(): DefaultMediaSourceFactory
    fun getActiveChannel(): TVChannel
}