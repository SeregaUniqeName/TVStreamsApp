package com.example.tvstreamsapp.presentation.openedChannel

import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.activity.OnBackPressedCallback
import androidx.annotation.OptIn
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.fragment.findNavController
import com.example.tvstreamsapp.R
import com.example.tvstreamsapp.databinding.OpenedChannelFragmentBinding
import com.example.tvstreamsapp.presentation.core.BaseFragment
import com.example.tvstreamsapp.presentation.models.TVChannelUiModel
import com.example.tvstreamsapp.presentation.utils.mapUiToDomain
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
                ExoPlayer.STATE_IDLE -> {
                    binding.playerProgress.visibility =
                        View.VISIBLE
                    binding.player.hideController()
                }

                ExoPlayer.STATE_BUFFERING -> {
                    binding.playerProgress.visibility =
                        View.VISIBLE
                    binding.player.hideController()
                }

                ExoPlayer.STATE_READY -> {
                    binding.player.hideController()
                    binding.playerProgress.visibility = View.GONE
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
                playerItemClick = object : PlayerItemClick {
                    override fun invoke(newItem: TVChannelUiModel) {
                        viewModel.changeChannelActive(newItem.mapUiToDomain())

                        val mediaItem = MediaItem.fromUri(viewModel.getActiveChannel().streamUri)
                        exoPlayer?.setMediaItem(mediaItem)
                        exoPlayer?.prepare()
                    }

                }
            )
            binding.playerRecyclerView?.adapter = adapter
            binding.playerRecyclerView?.itemAnimator = null
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

        setFullscreenClickListener()
        setBackPressedClickListener()

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

    private fun setFullscreenClickListener() {
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

    private fun setBackPressedClickListener() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    } else {
                        findNavController().popBackStack()
                    }
                }
            }
        )
    }

}