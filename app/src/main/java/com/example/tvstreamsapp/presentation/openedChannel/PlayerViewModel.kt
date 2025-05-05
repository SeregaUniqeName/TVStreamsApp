package com.example.tvstreamsapp.presentation.openedChannel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.exoplayer.ExoPlayer
import com.example.tvstreamsapp.domain.PlayerInteractor
import com.example.tvstreamsapp.domain.models.TVChannel
import com.example.tvstreamsapp.domain.useCases.ChangeChannelUseCase
import com.example.tvstreamsapp.domain.useCases.GetChannelsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class PlayerViewModel @Inject constructor(
    getChannelsUseCase: GetChannelsUseCase,
    private val playerInteractor: PlayerInteractor,
    private val changeChannelUseCase: ChangeChannelUseCase
) : ViewModel() {

    val channelsFlow = getChannelsUseCase()

    fun changeChannelActive(newItem: TVChannel) {
        viewModelScope.launch {
            changeChannelUseCase(newItem)
        }
    }

    fun releasePlayer() {
        playerInteractor.releasePlayer()
    }

    fun getPlayer(): ExoPlayer {
        return playerInteractor.getPlayer()
    }
}