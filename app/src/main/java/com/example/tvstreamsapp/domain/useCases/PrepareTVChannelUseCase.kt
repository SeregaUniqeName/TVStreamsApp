package com.example.tvstreamsapp.domain.useCases

import com.example.tvstreamsapp.domain.ChannelsRepository
import com.example.tvstreamsapp.domain.PlayerRepository
import com.example.tvstreamsapp.domain.models.TVChannel
import javax.inject.Inject

class PrepareTVChannelUseCase @Inject constructor(
    private val playerRepository: PlayerRepository,
    private val channelsRepository: ChannelsRepository,
) {

    operator fun invoke(item: TVChannel) {
        channelsRepository.refreshList(item)
        playerRepository.setChannel(item)
    }
}