package com.example.tvstreamsapp.data.remote

import com.example.tvstreamsapp.data.remote.models.ImageDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageApiService {

    @GET
    suspend fun getImageUrl(
        @Query("q") searchRequest: String,
        @Query("imgSize") size: String = "icon",
        @Query("num") first: Int = 1,
    ): ImageDTO
}