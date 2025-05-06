package com.example.tvstreamsapp.presentation.openedChannel

import androidx.lifecycle.ViewModel
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import com.example.tvstreamsapp.domain.models.TVChannel
import com.example.tvstreamsapp.domain.useCases.GetActiveChannelUseCase
import com.example.tvstreamsapp.domain.useCases.GetChannelsUseCase
import com.example.tvstreamsapp.domain.useCases.PrepareTVChannelUseCase
import com.example.tvstreamsapp.domain.useCases.ProvideMediaSourceUseCase
import javax.inject.Inject

class PlayerViewModel @Inject constructor(
    getChannelsUseCase: GetChannelsUseCase,
    private val provideMediaSourceUseCase: ProvideMediaSourceUseCase,
    private val getActiveChannelUseCase: GetActiveChannelUseCase,
    private val prepareTVChannelUseCase: PrepareTVChannelUseCase,
) : ViewModel() {

    val channelsFlow = getChannelsUseCase()
    var playWhenReady = true
    var currentItem = 0
    var playbackPosition = 0L

    fun provideMediaSourceFactory(): DefaultMediaSourceFactory {
        return provideMediaSourceUseCase()
    }

    fun getActiveChannel(): TVChannel {
        return getActiveChannelUseCase()
    }

    fun recordState(playWhenReady: Boolean, mediaItem: Int, currentPosition: Long) {
        this.playWhenReady = playWhenReady
        currentItem = mediaItem
        this.playbackPosition = currentPosition
    }

    fun changeChannelActive(item: TVChannel) {
        prepareTVChannelUseCase(item)
    }
}