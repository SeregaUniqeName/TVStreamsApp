package com.example.tvstreamsapp.domain

import androidx.media3.exoplayer.ExoPlayer

interface PlayerRepository {

    suspend fun openStream(streamUri: String)
    fun play()
    fun pause()
    fun release()
    fun getPlayer(): ExoPlayer
}