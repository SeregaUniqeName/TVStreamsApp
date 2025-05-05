package com.example.tvstreamsapp.presentation.channelsList

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.example.tvstreamsapp.databinding.ChannelListElementBinding
import com.example.tvstreamsapp.domain.models.TVChannel

class ChannelListAdapter(
    private val itemClick: ChannelItemClickListener,
) : ListAdapter<TVChannel, ChannelListViewHolder>(ChannelsListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelListViewHolder {
        return ChannelListViewHolder.ChannelHolder(
            ChannelListElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ChannelListViewHolder, position: Int) {
        val item = currentList[position]
        holder.root.setOnClickListener {
            itemClick(item)
        }
        holder.bind(item)
    }
}

abstract class ChannelListViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    abstract val root: LinearLayout
    abstract val image: ImageView
    abstract val text: TextView

    abstract fun bind(item: TVChannel)

    class ChannelHolder(binding: ChannelListElementBinding) : ChannelListViewHolder(binding) {
        override val root = binding.root
        override val image = binding.channelImage
        override val text = binding.channelText

        override fun bind(item: TVChannel) {
            Glide.with(image).load(item.iconUrl).into(image)
            text.text = item.channelName
        }

    }
}