package com.example.tvstreamsapp.domain.useCases

import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import com.example.tvstreamsapp.domain.PlayerRepository
import javax.inject.Inject

class ProvideMediaSourceUseCase @Inject constructor(
    private val repository: PlayerRepository
) {

    operator fun invoke(): DefaultMediaSourceFactory {
        return repository.provideMediaSource()
    }
}