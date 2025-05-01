package com.example.tvstreamsapp.presentation.openedChannel

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.tvstreamsapp.databinding.OpenedChannelListElementActiveBinding
import com.example.tvstreamsapp.databinding.OpenedChannelListElementBinding
import com.example.tvstreamsapp.presentation.openedChannel.uiModels.PlayerListItems
import com.example.tvstreamsapp.presentation.openedChannel.utils.mapUiToDomain

class PlayerListAdapter(
    private val playerItemClick: PlayerItemClick,
    private val typeList: List<PlayerListViewHolderType> = listOf(
        PlayerListViewHolderType.Active,
        PlayerListViewHolderType.Inactive
    )
) : ListAdapter<PlayerListItems, PlayerItemViewHolder>(PlayerListDiffCallback()) {

    private var activeItem = currentList.find { it is PlayerListItems.Active }

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
            activeItem = item
            playerItemClick(
                item.mapUiToDomain().changeActiveStatus(),
                activeItem!!.mapUiToDomain().changeActiveStatus()
            )
        }
        holder.bind(item)
    }
}

abstract class PlayerItemViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    abstract val root: LinearLayout
    abstract val image: ImageView
    abstract val text: TextView

    fun bind(item: PlayerListItems) {
        item.show(image, text)
    }

    class ActiveItemHolder(binding: OpenedChannelListElementActiveBinding) : PlayerItemViewHolder(binding) {
        override val root = binding.root
        override val image = binding.openedChannelImage
        override val text = binding.openedChannelText
    }

    class InactiveItemHolder(binding: OpenedChannelListElementBinding) : PlayerItemViewHolder(binding) {
        override val root = binding.root
        override val image = binding.openedChannelImage
        override val text = binding.openedChannelText
    }

}