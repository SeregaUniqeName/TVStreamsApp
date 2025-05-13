package com.example.tvstreamsapp.domain

import com.example.tvstreamsapp.domain.models.TVChannel
import kotlinx.coroutines.flow.Flow

interface ChannelsRepository {

    fun getChannels(): Flow<List<TVChannel>>

    fun refreshList(item: TVChannel)
}