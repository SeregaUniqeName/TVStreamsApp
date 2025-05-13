package com.example.tvstreamsapp.presentation.channelsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tvstreamsapp.domain.models.TVChannel
import com.example.tvstreamsapp.domain.useCases.GetChannelsUseCase
import com.example.tvstreamsapp.domain.useCases.PrepareTVChannelUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChannelsListViewModel @Inject constructor(
    getChannelsUseCase: GetChannelsUseCase,
    private val prepareTVChannelUseCase: PrepareTVChannelUseCase,
) : ViewModel() {

    val screenState = getChannelsUseCase()
        .filter { it.isNotEmpty() }
        .map { ChannelsState.Loaded(list = it) as ChannelsState }
        .catch { emit(ChannelsState.Error(message = it.message!!)) }
        .onStart { ChannelsState.Loading }

    fun loadTVChannel(item: TVChannel) {
        viewModelScope.launch {
            prepareTVChannelUseCase(item)
        }

    }
}