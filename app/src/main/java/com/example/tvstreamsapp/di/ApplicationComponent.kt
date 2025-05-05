package com.example.tvstreamsapp.di

import android.content.Context
import com.example.tvstreamsapp.di.annotations.ApplicationScope
import com.example.tvstreamsapp.presentation.channelsList.ChannelsListFragment
import com.example.tvstreamsapp.presentation.openedChannel.PlayerFragment
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
@ApplicationScope
interface ApplicationComponent {

    fun inject(fragment: ChannelsListFragment)
    fun inject(fragment: PlayerFragment)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance context: Context
        ): ApplicationComponent
    }
}