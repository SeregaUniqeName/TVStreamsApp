package com.example.tvstreamsapp.domain.models

data class TVChannel(
    val id: Long,
    val channelName: String,
    val iconUrl: String,
    val streamUri: String,
    val isActive: Boolean
) {

    fun changeActiveStatus(): TVChannel {
        return TVChannel(
            id = this.id,
            channelName = this.channelName,
            iconUrl = this.iconUrl,
            streamUri = this.streamUri,
            isActive = !this.isActive
        )
    }
}
