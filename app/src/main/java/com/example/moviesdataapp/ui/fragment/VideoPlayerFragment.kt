package com.example.moviesdataapp.ui.fragment

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.example.moviesdataapp.R
import com.example.moviesdataapp.databinding.FragmentVideoPlayerBinding
import com.example.moviesdataapp.di.component.Injectable
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class VideoPlayerFragment : Fragment(), Injectable {

    private lateinit var binding: FragmentVideoPlayerBinding
    private val args: VideoPlayerFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_video_player, container, false)

        initUI(binding)

        return binding.root
    }

    private fun initUI(binding: FragmentVideoPlayerBinding) {
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        requireActivity().window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        lifecycle.addObserver(binding.youtubePlayerView)

        args.videoID.let {
            binding.youtubePlayerView.addYouTubePlayerListener(object :
                AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {

                    youTubePlayer.loadVideo(
                        it,
                        0f
                    )
                }
            })
        }
    }

}