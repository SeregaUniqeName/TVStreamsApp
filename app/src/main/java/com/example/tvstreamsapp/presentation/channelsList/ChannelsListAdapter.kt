package com.example.tvstreamsapp.presentation.channelsList

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.tvstreamsapp.R
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
    abstract val progress: ProgressBar

    abstract fun bind(item: TVChannel)

    class ChannelHolder(binding: ChannelListElementBinding) : ChannelListViewHolder(binding) {
        override val root = binding.root
        override val image = binding.channelImage
        override val text = binding.channelText
        override val progress = binding.channelImageProgress

        override fun bind(item: TVChannel) {
            Glide.with(image)
                .load(item.iconUrl)
                .addListener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>,
                        isFirstResource: Boolean
                    ): Boolean {
                        progress.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: Target<Drawable>?,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        progress.visibility = View.GONE
                        return false
                    }

                })
                .error(R.drawable.ic_icon_failed)
                .into(image)
            text.text = item.channelName
        }

    }
}