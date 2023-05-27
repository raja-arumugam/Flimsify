package com.example.moviesdataapp.ui.fragment

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.moviesdataapp.R
import com.example.moviesdataapp.data.api.Result
import com.example.moviesdataapp.databinding.FragmentExploreBinding
import com.example.moviesdataapp.di.component.Injectable
import com.example.moviesdataapp.di.component.injectViewModel
import com.example.moviesdataapp.ui.adapter.ExploreGenreListAdapter
import com.example.moviesdataapp.ui.adapter.UpcomingAdapter
import com.example.moviesdataapp.ui.viewmodel.ExploreViewModel
import com.example.moviesdataapp.utils.anim.ViewPagerCubeInScalingTransformation
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class ExploreFragment : Fragment(), Injectable, MotionLayout.TransitionListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: FragmentExploreBinding
    private lateinit var viewModel: ExploreViewModel

    private lateinit var motionLayout: MotionLayout
    private lateinit var viewpagerImage: ViewPager2
    private lateinit var upcomingAdapter: UpcomingAdapter
    private lateinit var exploreGenreListAdapter: ExploreGenreListAdapter

    var lastProgress = 0f
//    private lateinit var handler: Handler

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_explore, container, false)

        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

        viewModel = injectViewModel(viewModelFactory)
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.getData()

        initUpcoming(binding)
        initMovieGenre(binding)

        motionLayout = binding.motionLayout

        /*motionLayout.transitionToEnd()
        Handler().postDelayed({
            motionLayout.apply {
                transitionToStart()
                setTransitionListener(this@ExploreFragment)
            }
        }, 90)*/

        return binding.root
    }

    private fun initUpcoming(binding: FragmentExploreBinding) {
        viewModel.upcomingPlayList.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    result.data?.let {
                        upcomingAdapter = UpcomingAdapter(requireActivity(), it)
                        viewpagerImage.adapter = upcomingAdapter
                        upcomingAdapter.onClick = ::onUpcomingMovies

                        // Viewpager2 callback listener
                        viewpagerImage.registerOnPageChangeCallback(object :
                            ViewPager2.OnPageChangeCallback() {
                            override fun onPageScrolled(
                                position: Int,
                                positionOffset: Float,
                                positionOffsetPixels: Int
                            ) {
                            }

                            override fun onPageSelected(position: Int) {
                                super.onPageSelected(position)

                                viewpagerImage.setPageTransformer(
                                    ViewPagerCubeInScalingTransformation()
                                )
                            }

                            override fun onPageScrollStateChanged(state: Int) {
                                super.onPageScrollStateChanged(state)
                            }
                        })
                    }
                }

                Result.Status.LOADING -> {
                }

                Result.Status.ERROR -> {
                    Snackbar.make(binding.root, result.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })

        viewpagerImage = binding.vpImages
        binding.root.layoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        viewpagerImage.offscreenPageLimit = 3
        viewpagerImage.clipToPadding = false
        viewpagerImage.clipChildren = false
        viewpagerImage.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
    }

    private fun initMovieGenre(binding: FragmentExploreBinding) {
        viewModel.movieGenresList.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    result.data.let {
                        if (it!!.isNotEmpty()) {

                            exploreGenreListAdapter = ExploreGenreListAdapter(requireActivity(), it.sortedBy {
                                it.name
                            })
                            binding.rvExploreGenre.layoutManager = GridLayoutManager(context, 2)
                            binding.rvExploreGenre.adapter = exploreGenreListAdapter
                            exploreGenreListAdapter.onClick = ::onGenreListClick
                        }
                    }
                }

                Result.Status.LOADING -> {

                }

                Result.Status.ERROR -> {
                    Snackbar.make(binding.root, result.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    override fun onTransitionStarted(motionLayout: MotionLayout?, startId: Int, endId: Int) {
    }

    override fun onTransitionChange(
        motionLayout: MotionLayout?,
        startId: Int,
        endId: Int,
        progress: Float
    ) {
        if (motionLayout == null)
            return

        if (progress - lastProgress > 0 && Math.abs(progress - 1f) < 0.1f) {
            // from start to end
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                activity!!.window?.decorView?.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else if (progress < 0.8f) {
            // from end to start
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                activity!!.window?.decorView?.systemUiVisibility = 0
        }
        lastProgress = progress
    }

    override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
    }

    override fun onTransitionTrigger(
        motionLayout: MotionLayout?,
        triggerId: Int,
        positive: Boolean,
        progress: Float
    ) {
    }

    private fun onGenreListClick(name: String, id: Int, path: Int) {
        val direction = ExploreFragmentDirections.actionExploreFragmentToGenreFragment(name, id, path)
        findNavController().navigate(direction)
    }

    // UpcomingMovies Adapter Click
    private fun onUpcomingMovies(id: Int) {
        val direction = ExploreFragmentDirections.actionExploreFragmentToDetailsFragment(id,true)
        findNavController().navigate(direction)
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }
}