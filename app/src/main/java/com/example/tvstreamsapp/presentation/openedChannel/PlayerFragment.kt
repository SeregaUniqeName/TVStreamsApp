package com.example.tvstreamsapp.presentation.openedChannel

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.tvstreamsapp.databinding.OpenedChannelFragmentBinding
import com.example.tvstreamsapp.domain.models.TVChannel
import com.example.tvstreamsapp.presentation.core.BaseFragment
import kotlinx.coroutines.launch

class PlayerFragment : BaseFragment<OpenedChannelFragmentBinding>() {

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelsFactory)[PlayerViewModel::class.java]
    }

    private var exoPlayer: ExoPlayer? = null
    private var isFullscreen = false

    override fun bind(inflater: LayoutInflater, container: ViewGroup?): OpenedChannelFragmentBinding {
        return OpenedChannelFragmentBinding.inflate(inflater, container, false)
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!isFullscreen) {
            val adapter = PlayerListAdapter(
                object : PlayerItemClick {
                    override fun invoke(newItem: TVChannel) {
                        viewModel.changeChannelActive(newItem)
                    }

                }
            )
            binding.playerRecyclerView?.adapter = adapter
            observeList(adapter)
        }
        setupPlayer()
        setupPlayerListeners()
    }

    private fun observeList(adapter: PlayerListAdapter) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.channelsFlow.collect { list ->
                    adapter.submitList(list)
                }
            }
        }
    }

    private fun setupPlayer() {
        exoPlayer = viewModel.getPlayer()
        binding.player.player = exoPlayer
    }

    private fun setupPlayerListeners() {
        binding.fullscreenButton.setOnClickListener {
            if (isFullscreen) {
                activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                isFullscreen = false
            } else {
                activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                isFullscreen = true
            }
        }
        exoPlayer?.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                if (playbackState == Player.STATE_BUFFERING) {
                    binding.playerProgressBar.visibility = View.VISIBLE
                } else {
                    binding.playerProgressBar.visibility = View.GONE
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.releasePlayer()
    }
}