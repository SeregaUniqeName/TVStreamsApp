package com.example.tvstreamsapp.domain

import androidx.media3.exoplayer.ExoPlayer
import javax.inject.Inject

class PlayerInteractor @Inject constructor(
    private val repository: PlayerRepository
) {

    fun releasePlayer() {
        repository.release()
    }

    fun getPlayer(): ExoPlayer {
        return repository.returnPlayer()
    }
}