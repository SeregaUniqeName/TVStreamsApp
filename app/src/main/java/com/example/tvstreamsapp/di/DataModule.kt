package com.example.tvstreamsapp.di

import android.content.Context
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import com.example.tvstreamsapp.data.local.database.ChannelsDao
import com.example.tvstreamsapp.data.local.database.ChannelsDatabase
import com.example.tvstreamsapp.data.remote.HLSConnectionService
import com.example.tvstreamsapp.data.repositories.ChannelsRepositoryImpl
import com.example.tvstreamsapp.data.repositories.PlayerRepositoryImpl
import com.example.tvstreamsapp.di.annotations.ApplicationScope
import com.example.tvstreamsapp.domain.ChannelsRepository
import com.example.tvstreamsapp.domain.PlayerRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindChannelsRepository(impl: ChannelsRepositoryImpl): ChannelsRepository

    @OptIn(UnstableApi::class)
    @Binds
    @ApplicationScope
    fun bindPlayerRepository(impl: PlayerRepositoryImpl): PlayerRepository

    companion object {

        @OptIn(UnstableApi::class)
        @Provides
        @ApplicationScope
        fun provideHLSConnectionService(): DefaultMediaSourceFactory {
            return HLSConnectionService.createMediaSourceFactory()
        }

        @Provides
        @ApplicationScope
        fun provideChannelsDao(
            context: Context
        ): ChannelsDao {
            return ChannelsDatabase.getInstance(context).channelsDao()
        }
    }
}