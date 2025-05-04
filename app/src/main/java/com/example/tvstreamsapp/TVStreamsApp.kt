package com.example.tvstreamsapp

import android.app.Application
import com.example.tvstreamsapp.di.DaggerApplicationComponent

class TVStreamsApp : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}