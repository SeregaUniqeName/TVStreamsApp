package com.example.tvstreamsapp.di

import android.app.Application
import com.example.tvstreamsapp.di.annotations.ApplicationScope
import com.example.tvstreamsapp.presentation.core.BaseFragment
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

    fun inject(fragment: BaseFragment<*>)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}