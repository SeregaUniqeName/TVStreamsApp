package com.example.tvstreamsapp.presentation.openedChannel

import com.example.tvstreamsapp.domain.PlayerRepository
import javax.inject.Inject

class PlayerInteractor @Inject constructor(
    private val repository: PlayerRepository
) {

    fun releasePlayer() {
        repository.release()
    }
}