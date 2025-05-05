package com.example.tvstreamsapp.presentation.channelsList

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tvstreamsapp.R
import com.example.tvstreamsapp.databinding.ChannelsListFragmentBinding
import com.example.tvstreamsapp.domain.models.TVChannel
import com.example.tvstreamsapp.presentation.core.BaseFragment
import kotlinx.coroutines.launch

class ChannelsListFragment : BaseFragment<ChannelsListFragmentBinding>() {

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelsFactory)[ChannelsListViewModel::class.java]
    }

    private val itemClick by lazy {
        object : ChannelItemClickListener {
            override fun invoke(item: TVChannel) {
                viewModel.loadTVChannel(item)
                navigate()
            }
        }
    }

    private val recyclerAdapter by lazy {
        ChannelListAdapter(itemClick)
    }

    override fun bind(inflater: LayoutInflater, container: ViewGroup?): ChannelsListFragmentBinding {
        return ChannelsListFragmentBinding.inflate(inflater, container, false)
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViews()
        binding.recyclerView.adapter = recyclerAdapter
    }

    private fun observeViews() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.screenState
                    .collect {
                        drawState(it)
                    }
            }
        }
    }

    private fun drawState(state: ChannelsState) {
        when (state) {
            is ChannelsState.Error -> {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(this.context, state.message, Toast.LENGTH_LONG).show()
            }

            is ChannelsState.Loaded -> {
                binding.progressBar.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
                recyclerAdapter.submitList(state.list)
            }

            ChannelsState.Loading -> {
                binding.progressBar.visibility = View.VISIBLE
            }
        }
    }

    private fun navigate() {
        findNavController().navigate(R.id.action_channelsListFragment_to_playerFragment)
    }
}