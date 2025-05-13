package com.example.tvstreamsapp.presentation.openedChannel

import com.example.tvstreamsapp.presentation.models.TVChannelUiModel

interface PlayerItemClick {

    operator fun invoke(newItem: TVChannelUiModel)
}