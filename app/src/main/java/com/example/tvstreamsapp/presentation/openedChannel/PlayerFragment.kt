package com.example.tvstreamsapp.presentation.openedChannel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.tvstreamsapp.databinding.OpenedChannelFragmentBinding
import com.example.tvstreamsapp.domain.models.TVChannel
import com.example.tvstreamsapp.presentation.core.BaseFragment
import com.example.tvstreamsapp.presentation.openedChannel.utils.mapDomainToUi
import kotlinx.coroutines.launch

class PlayerFragment : BaseFragment<OpenedChannelFragmentBinding>() {

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelsFactory)[PlayerViewModel::class.java]
    }

    override fun bind(inflater: LayoutInflater, container: ViewGroup?): OpenedChannelFragmentBinding {
        return OpenedChannelFragmentBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = PlayerListAdapter(
            object : PlayerItemClick {
                override fun invoke(newItem: TVChannel, oldItem: TVChannel) {
                    viewModel.changeItemActive(newItem, oldItem)
                }

            }
        )
        binding.playerRecyclerView?.adapter = adapter
        observeList(adapter)
    }

    private fun observeList(adapter: PlayerListAdapter) {
        lifecycleScope.launch {
            viewModel.channelsFlow.collect { list ->
                adapter.submitList(list.map { it.mapDomainToUi() })
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.releasePlayer()
    }
}