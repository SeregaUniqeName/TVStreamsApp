package com.example.tvstreamsapp.presentation.openedChannel.uiModels

import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.tvstreamsapp.presentation.openedChannel.PlayerListViewHolderType

sealed class PlayerListItems(
    open val id: Long,
    open val channelName: String,
    open val iconUrl: String,
    open val streamUri: String,
) {

    abstract fun type(): PlayerListViewHolderType

    fun show(image: ImageView, text: TextView) {
        Glide.with(image).load(iconUrl).into(image)
        text.text = channelName
    }

    data class Active(
        override val id: Long,
        override val channelName: String,
        override val iconUrl: String,
        override val streamUri: String,
    ) : PlayerListItems(id, channelName, iconUrl, streamUri) {
        override fun type(): PlayerListViewHolderType {
            return PlayerListViewHolderType.Active
        }
    }

    data class Inactive(
        override val id: Long,
        override val channelName: String,
        override val iconUrl: String,
        override val streamUri: String,
    ) : PlayerListItems(id, channelName, iconUrl, streamUri) {
        override fun type(): PlayerListViewHolderType {
            return PlayerListViewHolderType.Inactive
        }
    }
}