package com.example.tvstreamsapp.presentation.openedChannel

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.tvstreamsapp.databinding.OpenedChannelListElementActiveBinding
import com.example.tvstreamsapp.databinding.OpenedChannelListElementBinding

interface PlayerListViewHolderType {

    fun viewHolder(parent: ViewGroup): PlayerItemViewHolder

    object Active : PlayerListViewHolderType {
        override fun viewHolder(parent: ViewGroup): PlayerItemViewHolder {
            return PlayerItemViewHolder.ActiveItemHolder(
                OpenedChannelListElementActiveBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }

    object Inactive : PlayerListViewHolderType {
        override fun viewHolder(parent: ViewGroup): PlayerItemViewHolder {
            return PlayerItemViewHolder.InactiveItemHolder(
                OpenedChannelListElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }
}