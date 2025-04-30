package com.example.tvstreamsapp.domain.useCases

import com.example.tvstreamsapp.domain.PlayerRepository
import javax.inject.Inject

class LoadTVChannelUseCase @Inject constructor(
    private val repository: PlayerRepository,
) {

    suspend operator fun invoke(streamUri: String) {
        repository.openStream(streamUri)
    }
}