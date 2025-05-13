package com.example.tvstreamsapp.presentation.openedChannel

import android.graphics.drawable.Drawable
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
import com.example.tvstreamsapp.databinding.PlayerListElementActiveBinding
import com.example.tvstreamsapp.databinding.PlayerListElementBinding
import com.example.tvstreamsapp.presentation.models.TVChannelUiModel

class PlayerListAdapter(
    private val typeList: List<PlayerChannelListHolderType> = listOf(
        PlayerChannelListHolderType.Active,
        PlayerChannelListHolderType.Inactive
    ),
    private val playerItemClick: PlayerItemClick,
) : ListAdapter<TVChannelUiModel, PlayerItemViewHolder>(PlayerListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerItemViewHolder {
        return typeList[viewType].viewHolder(parent)
    }

    override fun getItemViewType(position: Int): Int {
        val item = currentList[position]
        val type = item.type()
        val index = typeList.indexOf(type)
        if (index == -1)
            throw IllegalArgumentException("Add type $type in $this constructor argument (typeList)!")
        return index
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
    abstract val progress: ProgressBar

    fun bind(item: TVChannelUiModel) {
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

    class ActiveHolder(binding: PlayerListElementActiveBinding) : PlayerItemViewHolder(binding) {
        override val root = binding.root
        override val image = binding.openedChannelImage
        override val text = binding.openedChannelText
        override val progress = binding.channelImageProgress
    }

    class InactiveHolder(binding: PlayerListElementBinding) : PlayerItemViewHolder(binding) {
        override val root = binding.root
        override val image = binding.openedChannelImage
        override val text = binding.openedChannelText
        override val progress = binding.channelImageProgress
    }

}