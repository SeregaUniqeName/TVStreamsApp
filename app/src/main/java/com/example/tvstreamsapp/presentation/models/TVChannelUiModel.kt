package com.example.tvstreamsapp.presentation.models

import com.example.tvstreamsapp.presentation.openedChannel.PlayerChannelListHolderType

sealed class TVChannelUiModel(
    open val id: Long,
    open val channelName: String,
    open val iconUrl: String,
    open val streamUri: String,
) {

    abstract fun type(): PlayerChannelListHolderType

    data class Active(
        override val id: Long,
        override val channelName: String,
        override val iconUrl: String,
        override val streamUri: String,
    ) : TVChannelUiModel(id, channelName, iconUrl, streamUri) {

        override fun type(): PlayerChannelListHolderType {
            return PlayerChannelListHolderType.Active
        }
    }

    data class Inactive(
        override val id: Long,
        override val channelName: String,
        override val iconUrl: String,
        override val streamUri: String,
    ) : TVChannelUiModel(id, channelName, iconUrl, streamUri) {

        override fun type(): PlayerChannelListHolderType {
            return PlayerChannelListHolderType.Inactive
        }
    }
}