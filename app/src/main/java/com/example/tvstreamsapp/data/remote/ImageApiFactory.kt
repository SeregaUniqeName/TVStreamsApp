package com.example.tvstreamsapp.data.remote

import com.example.tvstreamsapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ImageApiFactory {

    private const val BASE_URL = "https://customsearch.googleapis.com/customsearch/v1"
    private const val KEY = "key"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val currentRequest = chain.request()
            val newRequest = chain.request().url.newBuilder()
                .addQueryParameter(KEY, BuildConfig.API_KEY)
                .build()
            val newUrl = currentRequest.newBuilder()
                .url(newRequest)
                .build()
            chain.proceed(newUrl)
        }
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    val imageApiService: ImageApiService = retrofit.create()
}