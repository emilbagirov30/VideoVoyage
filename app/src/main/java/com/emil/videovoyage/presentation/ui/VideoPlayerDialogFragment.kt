package com.emil.videovoyage.presentation.ui

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import androidx.annotation.OptIn
import androidx.fragment.app.DialogFragment
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.emil.videovoyage.R
import com.emil.videovoyage.databinding.DialogPlayerBinding


class VideoPlayerDialogFragment : DialogFragment() {
    private lateinit var binding: DialogPlayerBinding
    private var videoUrl: String? = null
    private var name: String? = null
    private val exoPlayer: ExoPlayer by lazy {
        ExoPlayer.Builder(requireActivity()).build()
    }
    private var playbackPosition: Long = 0
    private var playbackState: Int = ExoPlayer.STATE_IDLE
    private var playWhenReady: Boolean = true


    companion object {
        private const val ARG_VIDEO_URL = "VIDEO_URL"
        private const val ARG_NAME = "NAME"
        private const val ARG_PLAYBACK_POSITION = "PLAYBACK_POSITION"
        private const val ARG_PLAYBACK_STATE = "PLAYBACK_STATE"
        private const val ARG_PLAY_WHEN_READY = "PLAY_WHEN_READY"
        private const val ORIENTATION_RESET_DELAY = 1000L
        fun newInstance(url: String?, name: String): VideoPlayerDialogFragment {
            val fragment = VideoPlayerDialogFragment()
            val args = Bundle()
            args.putString(ARG_VIDEO_URL, url)
            args.putString(ARG_NAME, name)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        videoUrl = arguments?.getString(ARG_VIDEO_URL)
        name = arguments?.getString(ARG_NAME)
        savedInstanceState?.let {
            playbackPosition = it.getLong(ARG_PLAYBACK_POSITION, 0)
            playbackState = it.getInt(ARG_PLAYBACK_STATE, ExoPlayer.STATE_IDLE)
            playWhenReady = it.getBoolean(ARG_PLAY_WHEN_READY, true)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DialogPlayerBinding.inflate(inflater)
        return binding.root
    }

    @OptIn(UnstableApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.playerView.player = exoPlayer
        val mediaItem = MediaItem.fromUri(Uri.parse(videoUrl))
        exoPlayer.setMediaItem(mediaItem)


        exoPlayer.seekTo(playbackPosition)
        exoPlayer.prepare()

        if (playbackState == ExoPlayer.STATE_READY) {
            exoPlayer.playWhenReady = playWhenReady
        } else {
            exoPlayer.playWhenReady = false
        }

        binding.videoNameTextView.text = name
        binding.playerView.setShowNextButton(false)
        binding.playerView.setShowPreviousButton(false)

        binding.toolBar.setNavigationOnClickListener {
            dismiss()
        }

        binding.playerView.setFullscreenButtonClickListener {
            toggleFullscreen()
        }

        binding.playerView.setControllerVisibilityListener(PlayerView.ControllerVisibilityListener { visibility ->
            binding.toolBar.visibility = if (visibility == View.VISIBLE) View.VISIBLE else View.GONE
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(ARG_PLAYBACK_POSITION, exoPlayer.currentPosition)
        outState.putInt(ARG_PLAYBACK_STATE, exoPlayer.playbackState)
        outState.putBoolean(ARG_PLAY_WHEN_READY, exoPlayer.playWhenReady)
    }


    @SuppressLint("SourceLockedOrientationActivity")
    private fun toggleFullscreen() {
        val activity = requireActivity()
        val currentOrientation = resources.configuration.orientation
        if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        } else {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        binding.playerView.postDelayed({
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }, ORIENTATION_RESET_DELAY)
    }

    override fun getTheme() = R.style.FullScreenDialog

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            setWindowAnimations(android.R.style.Animation_Dialog)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
                decorView.windowInsetsController?.hide(WindowInsets.Type.navigationBars())
            else {
                @Suppress("DEPRECATION")
                decorView.systemUiVisibility = (
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        )
            }
        }
    }

    override fun onStop() {
        super.onStop()
        if (!requireActivity().isChangingConfigurations) {
            exoPlayer.playWhenReady = false
        }
    }

    override fun onResume() {
        super.onResume()
        exoPlayer.playWhenReady = playWhenReady
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        exoPlayer.stop()
        exoPlayer.release()
    }
}
