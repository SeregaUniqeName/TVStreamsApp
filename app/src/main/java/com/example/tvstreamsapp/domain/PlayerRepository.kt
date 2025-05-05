package com.example.tvstreamsapp.domain

import androidx.media3.exoplayer.ExoPlayer
import com.example.tvstreamsapp.domain.models.TVChannel

interface PlayerRepository {

    suspend fun openStream(item: TVChannel)
    fun play()
    fun pause()
    fun release()
    fun returnPlayer(): ExoPlayer
    suspend fun changeChannel(newItem: TVChannel)
}