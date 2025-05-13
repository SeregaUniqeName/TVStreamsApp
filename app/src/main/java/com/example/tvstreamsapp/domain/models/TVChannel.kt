package com.example.tvstreamsapp.domain.models

data class TVChannel(
    val id: Long,
    val channelName: String,
    val iconUrl: String,
    val streamUri: String,
    val isActive: Boolean,
) {

    fun changeActive(): TVChannel {
        return this.copy(isActive = !isActive)
    }
}
