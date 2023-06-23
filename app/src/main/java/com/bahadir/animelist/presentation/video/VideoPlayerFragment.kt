package com.bahadir.animelist.presentation.video

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bahadir.animelist.R
import com.bahadir.animelist.common.extensions.screenOrientationUnspecified
import com.bahadir.animelist.common.extensions.snackBar
import com.bahadir.animelist.databinding.FragmentVideoPlayerBinding
import com.bahadir.animelist.delegation.viewBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.DefaultPlayerUiController
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class VideoPlayerFragment : Fragment(R.layout.fragment_video_player) {
    private val binding by viewBinding(FragmentVideoPlayerBinding::bind)
    private val args: VideoPlayerFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        fullScreen()
        super.onViewCreated(view, savedInstanceState)

        lifecycle.addObserver(binding.videoYoutube)
        binding.apply {
            val listener = object : AbstractYouTubePlayerListener() {
                override fun onError(
                    youTubePlayer: YouTubePlayer,
                    error: PlayerConstants.PlayerError
                ) {
                    super.onError(youTubePlayer, error)
                    when (error) {
                        PlayerConstants.PlayerError.VIDEO_NOT_FOUND -> {
                            requireView().snackBar("Video not found")
                        }

                        PlayerConstants.PlayerError.VIDEO_NOT_PLAYABLE_IN_EMBEDDED_PLAYER -> {
                            requireView().snackBar("Video not playable in embedded player")
                        }

                        else -> {
                            requireView().snackBar("Unknown error")
                        }
                    }
                }

                override fun onReady(youTubePlayer: YouTubePlayer) {
                    super.onReady(youTubePlayer)
                    videoYoutube.setCustomPlayerUi(
                        DefaultPlayerUiController(
                            videoYoutube,
                            youTubePlayer
                        ).rootView
                    )
                    youTubePlayer.loadVideo(args.videoId, 0f)

                }
            }

            val options: IFramePlayerOptions = IFramePlayerOptions.Builder()
                .controls(1)
                .build()
            videoYoutube.initialize(listener, options)

            toolbar.setStartIconOnclick {
                exitFullscreen()
                findNavController().popBackStack()

            }

        }
    }

    private fun fullScreen() {
        requireActivity().window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    private fun exitFullscreen() {
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        requireActivity().window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        exitFullscreen()
        requireActivity().screenOrientationUnspecified()
    }
}