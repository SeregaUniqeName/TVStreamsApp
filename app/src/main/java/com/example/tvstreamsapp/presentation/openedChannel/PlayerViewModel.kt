package com.example.tvstreamsapp.presentation.openedChannel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.exoplayer.ExoPlayer
import com.example.tvstreamsapp.domain.PlayerInteractor
import com.example.tvstreamsapp.domain.models.TVChannel
import com.example.tvstreamsapp.domain.useCases.GetChannelsUseCase
import com.example.tvstreamsapp.domain.useCases.LoadTVChannelUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class PlayerViewModel @Inject constructor(
    getChannelsUseCase: GetChannelsUseCase,
    private val loadTVChannelUseCase: LoadTVChannelUseCase,
    private val playerInteractor: PlayerInteractor
) : ViewModel() {

    val channelsFlow = getChannelsUseCase()

    fun openStream(item: TVChannel) {
        viewModelScope.launch {
            loadTVChannelUseCase.invoke(item)
        }
    }

    fun changeItemActive(hewItem: TVChannel, oldItem: TVChannel) {

    }

    fun releasePlayer() {
        playerInteractor.releasePlayer()
    }

    fun getPlayer(): ExoPlayer {
        return playerInteractor.getPlayer()
    }
}