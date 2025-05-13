package com.example.tvstreamsapp.presentation.openedChannel

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.tvstreamsapp.databinding.PlayerListElementActiveBinding
import com.example.tvstreamsapp.databinding.PlayerListElementBinding

sealed class PlayerChannelListHolderType {

    abstract fun viewHolder(parent: ViewGroup): PlayerItemViewHolder

    data object Active : PlayerChannelListHolderType() {

        override fun viewHolder(parent: ViewGroup): PlayerItemViewHolder {
            return PlayerItemViewHolder.ActiveHolder(
                PlayerListElementActiveBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }

    data object Inactive : PlayerChannelListHolderType() {

        override fun viewHolder(parent: ViewGroup): PlayerItemViewHolder {
            return PlayerItemViewHolder.InactiveHolder(
                PlayerListElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }
}