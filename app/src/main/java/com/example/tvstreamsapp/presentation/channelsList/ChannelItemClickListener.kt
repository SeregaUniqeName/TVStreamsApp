package com.example.tvstreamsapp.presentation.channelsList

import com.example.tvstreamsapp.domain.models.TVChannel

interface ChannelItemClickListener {

    operator fun invoke(item: TVChannel)
}
