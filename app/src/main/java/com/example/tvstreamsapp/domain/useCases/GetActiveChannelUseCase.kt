package com.example.tvstreamsapp.domain.useCases

import com.example.tvstreamsapp.domain.PlayerRepository
import com.example.tvstreamsapp.domain.models.TVChannel
import javax.inject.Inject

class GetActiveChannelUseCase @Inject constructor(
    private val repository: PlayerRepository
) {

    operator fun invoke(): TVChannel {
        return repository.getActiveChannel()
    }
}