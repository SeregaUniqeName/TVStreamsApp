package com.example.tvstreamsapp.di

import androidx.lifecycle.ViewModel
import com.example.tvstreamsapp.presentation.channelsList.ChannelsListViewModel
import com.example.tvstreamsapp.presentation.openedChannel.PlayerViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ChannelsListViewModel::class)
    fun bindChannelsListViewModel(viewModel: ChannelsListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PlayerViewModel::class)
    fun bindPlayerViewModel(viewModel: PlayerViewModel): ViewModel
}