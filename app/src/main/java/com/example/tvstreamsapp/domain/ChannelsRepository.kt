package com.example.tvstreamsapp.domain

import com.example.tvstreamsapp.domain.models.TVChannel
import kotlinx.coroutines.flow.Flow

interface ChannelsRepository {

    val channelsFlow: Flow<List<TVChannel>>
}