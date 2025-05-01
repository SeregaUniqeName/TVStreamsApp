package com.example.tvstreamsapp.domain.useCases

import com.example.tvstreamsapp.domain.PlayerRepository
import com.example.tvstreamsapp.domain.models.TVChannel
import javax.inject.Inject

class LoadTVChannelUseCase @Inject constructor(
    private val repository: PlayerRepository,
) {

    suspend operator fun invoke(item: TVChannel) {
        repository.openStream(item)
    }
}