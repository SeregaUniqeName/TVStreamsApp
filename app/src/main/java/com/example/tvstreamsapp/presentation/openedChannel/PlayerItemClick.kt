package com.example.tvstreamsapp.presentation.openedChannel

import com.example.tvstreamsapp.domain.models.TVChannel

interface PlayerItemClick {

    operator fun invoke(newItem: TVChannel, oldItem: TVChannel)
}