package com.example.tvstreamsapp.domain.useCases

import com.example.tvstreamsapp.domain.ChannelsRepository
import com.example.tvstreamsapp.domain.models.TVChannel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetChannelsUseCase @Inject constructor(
    private val repository: ChannelsRepository
){

    operator fun invoke() : StateFlow<List<TVChannel>> {
        return repository.getChannels()
    }
}