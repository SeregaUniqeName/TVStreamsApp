package com.example.tvstreamsapp.presentation.openedChannel

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.OptIn
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
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
//    private val playbackStateListener: Player.Listener = object : Player.Listener {
//        override fun onPlaybackStateChanged(playbackState: Int) {
//            when (playbackState) {
//                ExoPlayer.STATE_IDLE -> binding.player.visibility = View.VISIBLE
//                ExoPlayer.STATE_BUFFERING -> binding.playerProgressBar.visibility = View.VISIBLE
//                ExoPlayer.STATE_READY -> {
//                    hideSystemUi()
//                    binding.playerProgressBar.visibility = View.GONE
//                }
//                ExoPlayer.STATE_ENDED -> showSystemUi()
//            }
//        }
//
//    }

    override fun bind(inflater: LayoutInflater, container: ViewGroup?): OpenedChannelFragmentBinding {
        return OpenedChannelFragmentBinding.inflate(inflater, container, false)
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    @OptIn(UnstableApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.aspectRatio?.setAspectRatio(16F / 9f)

        if (!isFullscreen) {
            val adapter = PlayerListAdapter(
                object : PlayerItemClick {
                    override fun invoke(newItem: TVChannel) {
                        viewModel.changeChannelActive(newItem)

                        val mediaItem = MediaItem.fromUri(viewModel.getActiveChannel().streamUri)
                        exoPlayer?.setMediaItem(mediaItem)
                        exoPlayer?.prepare()
                    }

                }
            )
            binding.playerRecyclerView?.adapter = adapter
            observeList(adapter)
        }
    }

    override fun onStart() {
        super.onStart()
        initializePlayer()
    }

    override fun onResume() {
        super.onResume()
        hideSystemUi()
        exoPlayer?.playWhenReady = true
    }

    override fun onPause() {
        super.onPause()
        exoPlayer?.playWhenReady = false
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
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

    private fun initializePlayer() {
        exoPlayer = ExoPlayer.Builder(requireActivity())
            .setMediaSourceFactory(viewModel.provideMediaSourceFactory())
            .build()
            .also { exoPlayer ->
                binding.player.player = exoPlayer

                val mediaItem = MediaItem.fromUri(viewModel.getActiveChannel().streamUri)
                exoPlayer.setMediaItem(mediaItem)

                exoPlayer.playWhenReady = viewModel.playWhenReady
                exoPlayer.seekTo(viewModel.currentItem, viewModel.playbackPosition)
//                exoPlayer.addListener(playbackStateListener)
                exoPlayer.prepare()
            }
    }

    private fun hideSystemUi() {
        WindowCompat.setDecorFitsSystemWindows(requireActivity().window, false)
        WindowInsetsControllerCompat(requireActivity().window, binding.player).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    private fun showSystemUi() {
        WindowCompat.setDecorFitsSystemWindows(requireActivity().window, false)
        WindowInsetsControllerCompat(requireActivity().window, binding.player).let { controller ->
            controller.show(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    private fun releasePlayer() {
        exoPlayer?.let { exoPlayer ->
            viewModel.recordState(
                exoPlayer.playWhenReady,
                exoPlayer.currentMediaItemIndex,
                exoPlayer.currentPosition
            )
//            exoPlayer.removeListener(playbackStateListener)
            exoPlayer.release()
        }
        exoPlayer = null
    }

}