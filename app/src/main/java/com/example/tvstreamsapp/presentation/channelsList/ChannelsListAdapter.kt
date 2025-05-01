package com.example.tvstreamsapp.presentation.channelsList

import android.view.LayoutInflater
import android.view.ViewGroup
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
        val binding = holder.binding
        val item = currentList[position]
        binding.root.setOnClickListener {
            itemClick(item)
        }
        holder.bind(item)
    }
}

abstract class ChannelListViewHolder(open val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    abstract fun bind(item: TVChannel)

    class ChannelHolder(override val binding: ChannelListElementBinding) : ChannelListViewHolder(binding) {
        override fun bind(item: TVChannel) {
            val image = binding.channelImage
            Glide.with(binding.root).load(item.iconUrl).into(image)
            binding.channelText.text = item.channelName
        }

    }
}