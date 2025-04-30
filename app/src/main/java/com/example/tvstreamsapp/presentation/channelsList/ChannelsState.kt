package com.example.tvstreamsapp.presentation.channelsList

import com.example.tvstreamsapp.domain.models.TVChannel

sealed class ChannelsState {

    data object Loading : ChannelsState()

    data class Loaded(
        val list: List<TVChannel>
    ) : ChannelsState()

    data class Error(
        val message: String
    ) : ChannelsState()
}