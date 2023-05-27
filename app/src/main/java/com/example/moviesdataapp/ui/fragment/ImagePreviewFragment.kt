package com.example.moviesdataapp.ui.fragment

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.example.moviesdataapp.R
import com.example.moviesdataapp.databinding.FragmentImagePreviewBinding
import com.example.moviesdataapp.di.component.Injectable
import com.example.moviesdataapp.ui.adapter.ImagePreviewAdapter
import com.example.moviesdataapp.utils.anim.ViewPagerCubeInScalingTransformation
import com.example.moviesdataapp.utils.anim.ViewPagerCubeOutPageTransformer
import javax.inject.Inject

class ImagePreviewFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var binding: FragmentImagePreviewBinding
    private val navArgs: ImagePreviewFragmentArgs by navArgs()
    private lateinit var adapter: ImagePreviewAdapter
    private lateinit var viewpagerImages: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_image_preview, container, false)

        navArgs.let {
            it.images
            it.position
            it.isFrom

            if (it.isFrom) {
                requireActivity().requestedOrientation =
                    ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
                requireActivity().window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_FULLSCREEN
            } else {
                requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            }

            binding.lifecycleOwner = viewLifecycleOwner

            viewpagerImages = binding.vpImages

            binding.imageCounterTV.text =
                StringBuilder().append(navArgs.position + 1).append(" / ")
                    .append(navArgs.images.size).toString()

            adapter =
                ImagePreviewAdapter(it.images)
            viewpagerImages.adapter = adapter

            viewpagerImages.apply {
                setCurrentItem(navArgs.position, true)
            }

            // Viewpager2 callback listener
            viewpagerImages.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                }

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                    viewpagerImages.setPageTransformer(ViewPagerCubeInScalingTransformation())

                    binding.imageCounterTV.text =
                        StringBuilder().append(position + 1).append(" / ")
                            .append(navArgs.images.size).toString()
                }

                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
                }
            })
        }

        binding.closeBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()

    }

}