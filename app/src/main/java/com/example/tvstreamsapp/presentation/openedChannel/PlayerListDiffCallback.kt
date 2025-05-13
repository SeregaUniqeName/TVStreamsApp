package com.example.tvstreamsapp.presentation.openedChannel

import androidx.recyclerview.widget.DiffUtil
import com.example.tvstreamsapp.presentation.models.TVChannelUiModel

class PlayerListDiffCallback : DiffUtil.ItemCallback<TVChannelUiModel>() {

    override fun areItemsTheSame(oldItem: TVChannelUiModel, newItem: TVChannelUiModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: TVChannelUiModel, newItem: TVChannelUiModel): Boolean {
        return oldItem == newItem
    }
}