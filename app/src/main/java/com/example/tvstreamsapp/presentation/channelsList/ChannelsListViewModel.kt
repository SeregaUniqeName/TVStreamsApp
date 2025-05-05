package com.example.tvstreamsapp.presentation.channelsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tvstreamsapp.domain.models.TVChannel
import com.example.tvstreamsapp.domain.useCases.GetChannelsUseCase
import com.example.tvstreamsapp.domain.useCases.LoadTVChannelUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChannelsListViewModel @Inject constructor(
    getChannelsUseCase: GetChannelsUseCase,
    private val loadTVChannelUseCase: LoadTVChannelUseCase,
) : ViewModel() {

    val screenState = getChannelsUseCase()
        .map { ChannelsState.Loaded(list = it) as ChannelsState }
        .onStart { emit(ChannelsState.Loading) }
        .catch { error ->
            emit(ChannelsState.Error(message = error.message.toString()))
        }

    fun loadTVChannel(item: TVChannel) {
        viewModelScope.launch {
            loadTVChannelUseCase(item)
        }

    }
}