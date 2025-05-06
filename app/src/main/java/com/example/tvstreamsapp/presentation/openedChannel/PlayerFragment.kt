package com.example.tvstreamsapp.presentation.openedChannel

import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.annotation.OptIn
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.example.tvstreamsapp.R
import com.example.tvstreamsapp.databinding.OpenedChannelFragmentBinding
import com.example.tvstreamsapp.domain.models.TVChannel
import com.example.tvstreamsapp.presentation.core.BaseFragment
import kotlinx.coroutines.launch

class PlayerFragment : BaseFragment<OpenedChannelFragmentBinding>() {

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelsFactory)[PlayerViewModel::class.java]
    }

    private var exoPlayer: ExoPlayer? = null
    private lateinit var fullscreenButton: ImageButton
    private val playbackStateListener: Player.Listener = object : Player.Listener {
        @OptIn(UnstableApi::class)
        override fun onPlaybackStateChanged(playbackState: Int) {
            when (playbackState) {
                ExoPlayer.STATE_IDLE -> binding.player.findViewById<View?>(R.id.player_buffering).visibility =
                    View.VISIBLE

                ExoPlayer.STATE_BUFFERING -> binding.player.findViewById<View?>(R.id.player_buffering).visibility =
                    View.VISIBLE

                ExoPlayer.STATE_READY -> {
                    binding.player.hideController()
                    binding.player.findViewById<View?>(R.id.player_buffering).visibility = View.GONE
                }

                ExoPlayer.STATE_ENDED -> binding.player.showController()
            }
        }

        @OptIn(UnstableApi::class)
        override fun onIsPlayingChanged(isPlaying: Boolean) {
            if (isPlaying) binding.player.hideController()
        }

    }

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

        fullscreenButton = binding.player.findViewById(R.id.fullscreen_custom)

        binding.aspectRatio.setAspectRatio(16F / 9f)

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
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

    @OptIn(UnstableApi::class)
    override fun onResume() {
        super.onResume()
        binding.player.hideController()
        exoPlayer?.playWhenReady = true

        fullscreenButton.setOnClickListener {
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                fullscreenButton.setImageResource(R.drawable.icon_fullscreen_exit)
                requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            } else if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                fullscreenButton.setImageResource(R.drawable.icon_fullscreen)
                requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            }
        }
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
                exoPlayer.addListener(playbackStateListener)
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
            exoPlayer.removeListener(playbackStateListener)
            exoPlayer.release()
        }
        exoPlayer = null
    }

}