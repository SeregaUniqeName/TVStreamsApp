package com.example.tvstreamsapp.domain

import com.example.tvstreamsapp.domain.models.TVChannel
import kotlinx.coroutines.flow.StateFlow

interface ChannelsRepository {

    val channelsFlow: StateFlow<List<TVChannel>>

}