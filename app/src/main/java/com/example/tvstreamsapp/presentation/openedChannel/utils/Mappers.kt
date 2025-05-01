package com.example.tvstreamsapp.presentation.openedChannel.utils

import com.example.tvstreamsapp.domain.models.TVChannel
import com.example.tvstreamsapp.presentation.openedChannel.uiModels.PlayerListItems

fun TVChannel.mapDomainToUi(): PlayerListItems {
    return if (this.isActive) {
        PlayerListItems.Active(
            id = this.id,
            channelName = this.channelName,
            iconUrl = this.iconUrl,
            streamUri = this.streamUri,
        )
    } else {
        PlayerListItems.Inactive(
            id = this.id,
            channelName = this.channelName,
            iconUrl = this.iconUrl,
            streamUri = this.streamUri,
        )
    }
}

fun PlayerListItems.mapUiToDomain(): TVChannel {
    return TVChannel(
        id = this.id,
        channelName = this.channelName,
        iconUrl = this.iconUrl,
        streamUri = this.streamUri,
        isActive = when (this) {
            is PlayerListItems.Active -> true
            is PlayerListItems.Inactive -> false
        }
    )
}