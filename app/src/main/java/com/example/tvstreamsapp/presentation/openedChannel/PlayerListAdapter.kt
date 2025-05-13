package com.example.tvstreamsapp.presentation.openedChannel

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.example.tvstreamsapp.databinding.PlayerListElementBinding
import com.example.tvstreamsapp.domain.models.TVChannel

class PlayerListAdapter(
    private val playerItemClick: PlayerItemClick,
) : ListAdapter<TVChannel, PlayerItemViewHolder>(PlayerListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerItemViewHolder {
        return PlayerItemViewHolder.PlayerItem(
            PlayerListElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: PlayerItemViewHolder, position: Int) {
        val item = currentList[position]
        holder.root.setOnClickListener {
            playerItemClick(
                item
            )
        }
        holder.bind(item)
    }
}

abstract class PlayerItemViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    abstract val root: LinearLayout
    abstract val image: ImageView
    abstract val text: TextView

    abstract fun bind(item: TVChannel)

    class PlayerItem(binding: PlayerListElementBinding) : PlayerItemViewHolder(binding) {
        override val root = binding.root
        override val image = binding.openedChannelImage
        override val text = binding.openedChannelText

        override fun bind(item: TVChannel) {

            Glide.with(image).load(item.iconUrl).into(image)
            text.text = item.channelName
        }
    }

}