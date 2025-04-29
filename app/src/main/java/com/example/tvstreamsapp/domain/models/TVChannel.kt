package com.example.tvstreamsapp.domain.models

data class TVChannel(
    private val id: Long,
    private val channelName: String,
    private val iconUrl: String,
    private val streamUri: String,
)
