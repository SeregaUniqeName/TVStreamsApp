package com.example.tvstreamsapp.presentation.channelsList

import androidx.recyclerview.widget.DiffUtil
import com.example.tvstreamsapp.domain.models.TVChannel

class ChannelListDiffCallback : DiffUtil.ItemCallback<TVChannel>() {
    override fun areItemsTheSame(oldItem: TVChannel, newItem: TVChannel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TVChannel, newItem: TVChannel): Boolean {
        return oldItem == newItem
    }
}