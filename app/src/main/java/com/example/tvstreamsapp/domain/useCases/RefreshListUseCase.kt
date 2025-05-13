package com.example.tvstreamsapp.domain.useCases

import com.example.tvstreamsapp.domain.ChannelsRepository
import com.example.tvstreamsapp.domain.models.TVChannel
import javax.inject.Inject

class RefreshListUseCase @Inject constructor(
    private val repository: ChannelsRepository
) {

    operator fun invoke(item: TVChannel) {
        repository.refreshList(item)
    }
}