package com.example.tvstreamsapp.domain.useCases

import com.example.tvstreamsapp.domain.PlayerRepository
import com.example.tvstreamsapp.domain.models.TVChannel
import javax.inject.Inject

class ChangeItemStatusUseCase @Inject constructor(
    private val repository: PlayerRepository
) {

    suspend operator fun invoke(newItem: TVChannel, oldItem: TVChannel) {
        repository.changeItemStatus(newItem, oldItem)
    }
}