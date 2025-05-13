package com.example.tvstreamsapp.presentation.utils

import com.example.tvstreamsapp.domain.models.TVChannel
import com.example.tvstreamsapp.presentation.models.TVChannelUiModel

fun TVChannel.mapDomainToUi(): TVChannelUiModel {
    return if (this.isActive) {
        TVChannelUiModel.Active(
            id = this.id,
            iconUrl = this.iconUrl,
            channelName = this.channelName,
            streamUri = this.streamUri,
        )
    } else {
        TVChannelUiModel.Inactive(
            id = this.id,
            iconUrl = this.iconUrl,
            channelName = this.channelName,
            streamUri = this.streamUri,
        )
    }
}

fun TVChannelUiModel.mapUiToDomain(): TVChannel {
    return when (this) {
        is TVChannelUiModel.Active -> TVChannel(
            id = this.id,
            iconUrl = this.iconUrl,
            channelName = this.channelName,
            streamUri = this.streamUri,
            isActive = true,
        )

        is TVChannelUiModel.Inactive -> TVChannel(
            id = this.id,
            iconUrl = this.iconUrl,
            channelName = this.channelName,
            streamUri = this.streamUri,
            isActive = false,
        )
    }
}
