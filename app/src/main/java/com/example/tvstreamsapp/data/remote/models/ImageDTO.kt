package com.example.tvstreamsapp.data.remote.models

import com.google.gson.annotations.SerializedName

data class ImageDTO(
    @SerializedName("items") val item: ItemDTO,
)

data class ItemDTO(
    @SerializedName("link") val imageUrl: String
)
