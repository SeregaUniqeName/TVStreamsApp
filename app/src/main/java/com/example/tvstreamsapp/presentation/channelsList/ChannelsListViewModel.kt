package com.example.tvstreamsapp.presentation.channelsList

import androidx.lifecycle.ViewModel
import com.example.tvstreamsapp.domain.useCases.GetChannelsUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class ChannelsListViewModel @Inject constructor(
    private val getChannelsUseCase: GetChannelsUseCase
) : ViewModel() {

    val screenState = getChannelsUseCase()
        .map { ChannelsState.Loaded(list = it) as ChannelsState }
        .onStart { ChannelsState.Loading }
        .catch { error ->
            emit(ChannelsState.Error(message = error.message.toString()))
        }
}