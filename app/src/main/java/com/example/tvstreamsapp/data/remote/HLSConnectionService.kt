package com.example.tvstreamsapp.data.remote

import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.okhttp.OkHttpDataSource
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import okhttp3.OkHttpClient

@UnstableApi
object HLSConnectionService {

    private val okHttpClient = OkHttpClient.Builder()
        .build()

    private val dataSourceFactory = OkHttpDataSource.Factory(okHttpClient)

    fun createMediaSourceFactory(): DefaultMediaSourceFactory {
        return DefaultMediaSourceFactory(dataSourceFactory)
    }

}