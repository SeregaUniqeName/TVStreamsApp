package com.example.tvstreamsapp.presentation.openedChannel

import androidx.recyclerview.widget.DiffUtil
import com.example.tvstreamsapp.presentation.openedChannel.uiModels.PlayerListItems

class PlayerListDiffCallback : DiffUtil.ItemCallback<PlayerListItems>() {

    override fun areItemsTheSame(oldItem: PlayerListItems, newItem: PlayerListItems): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PlayerListItems, newItem: PlayerListItems): Boolean {
        return oldItem == newItem
    }
}