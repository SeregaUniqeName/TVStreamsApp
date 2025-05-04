package com.example.tvstreamsapp.di

import android.app.Application
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import com.example.tvstreamsapp.data.local.database.ChannelsDao
import com.example.tvstreamsapp.data.local.database.ChannelsDatabase
import com.example.tvstreamsapp.data.repositories.ChannelsRepositoryImpl
import com.example.tvstreamsapp.data.repositories.PlayerRepositoryImpl
import com.example.tvstreamsapp.di.annotations.ApplicationScope
import com.example.tvstreamsapp.di.annotations.ChannelsListScope
import com.example.tvstreamsapp.di.annotations.PlayerViewScope
import com.example.tvstreamsapp.domain.ChannelsRepository
import com.example.tvstreamsapp.domain.PlayerRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    @ChannelsListScope
    fun bindChannelsRepository(impl: ChannelsRepositoryImpl): ChannelsRepository

    @OptIn(UnstableApi::class)
    @Binds
    @PlayerViewScope
    fun bindPlayerRepository(impl: PlayerRepositoryImpl): PlayerRepository

    companion object {

        @Provides
        @ApplicationScope
        fun provideChannelsDao(
            application: Application
        ): ChannelsDao {
            return ChannelsDatabase.getInstance(application).channelsDao()
        }
    }
}