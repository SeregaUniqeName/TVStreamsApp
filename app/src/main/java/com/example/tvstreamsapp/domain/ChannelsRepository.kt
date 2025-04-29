package com.example.tvstreamsapp.domain

import com.example.tvstreamsapp.domain.models.TVChannel
import kotlinx.coroutines.flow.StateFlow

interface ChannelsRepository {

    fun getChannels() : StateFlow<List<TVChannel>>
}