package com.example.tvstreamsapp.data.utils

import com.example.tvstreamsapp.data.local.model.TVChannelDb
import com.example.tvstreamsapp.domain.models.TVChannel

fun TVChannelDb.mapToDomain(): TVChannel {
    return TVChannel(
        id = this.id,
        channelName = this.channelName,
        iconUrl = this.iconUrl,
        streamUri = this.streamUri,
    )
}

fun TVChannel.mapDomainToDb(): TVChannelDb {
    return TVChannelDb(
        id = this.id,
        channelName = this.channelName,
        iconUrl = this.iconUrl,
        streamUri = this.streamUri,
    )
}