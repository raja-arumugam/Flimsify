package com.example.moviesdataapp.ui.fragment

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import androidx.viewpager2.widget.ViewPager2
import com.example.moviesdataapp.R
import com.example.moviesdataapp.data.api.Result
import com.example.moviesdataapp.databinding.FragmentMoviesBinding
import com.example.moviesdataapp.di.component.Injectable
import com.example.moviesdataapp.di.component.injectViewModel
import com.example.moviesdataapp.ui.adapter.ActionListAdapter
import com.example.moviesdataapp.ui.adapter.NowPlayingMoviesAdapter
import com.example.moviesdataapp.ui.adapter.PopularListAdapter
import com.example.moviesdataapp.ui.viewmodel.MoviesListViewModel
import com.example.moviesdataapp.utils.anim.ViewPagerCubeInScalingTransformation
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject


class MoviesFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: FragmentMoviesBinding
    private lateinit var viewModel: MoviesListViewModel

    private lateinit var viewpager_NowPlaying: ViewPager2
//    private lateinit var handler: Handler

    private lateinit var nowPlayingMoviesAdapter: NowPlayingMoviesAdapter
    private lateinit var popularListAdapter: PopularListAdapter
    private lateinit var actionListAdapter: ActionListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies, container, false)
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

        viewModel = injectViewModel(viewModelFactory)
        binding.lifecycleOwner = viewLifecycleOwner

        initAPINowPlaying(binding)
        initPopular(binding)
        initAction(binding)

        return binding.root
    }

    // Now playing API call and Adapter
    private fun initAPINowPlaying(
        binding: FragmentMoviesBinding
    ) {
        viewModel.nowPlayingList.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    binding.pbNowPlaying.visibility = View.GONE
                    result.data?.let {
                        nowPlayingMoviesAdapter =
                            NowPlayingMoviesAdapter(requireActivity(), it)
                        viewpager_NowPlaying.adapter = nowPlayingMoviesAdapter
                        nowPlayingMoviesAdapter.onClick = ::onNowPlayingMovies

                        // Viewpager2 callback listener
                        viewpager_NowPlaying.registerOnPageChangeCallback(object :
                            ViewPager2.OnPageChangeCallback() {
                            override fun onPageScrolled(
                                position: Int,
                                positionOffset: Float,
                                positionOffsetPixels: Int
                            ) {
                            }

                            override fun onPageSelected(position: Int) {
                                super.onPageSelected(position)

                                /*handler.removeCallbacks(runnable)
                                handler.postDelayed(runnable, 2000)*/

                                viewpager_NowPlaying.setPageTransformer(
                                    ViewPagerCubeInScalingTransformation()
                                )
                            }

                            override fun onPageScrollStateChanged(state: Int) {
                                super.onPageScrollStateChanged(state)
                            }
                        })
                    }
                }
                Result.Status.LOADING -> binding.pbNowPlaying.visibility = View.VISIBLE
                Result.Status.ERROR -> {
                    binding.pbNowPlaying.visibility = View.GONE
                    Snackbar.make(binding.root, result.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })

        viewpager_NowPlaying = binding.vpNowplayingMoviesList
        binding.root!!.layoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

        viewpager_NowPlaying.offscreenPageLimit = 3
        viewpager_NowPlaying.clipToPadding = false
        viewpager_NowPlaying.clipChildren = false
        viewpager_NowPlaying.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

//        handler = Handler(Looper.myLooper()!!)
    }

    // Popular Movies Adapter
    fun initPopular(binding: FragmentMoviesBinding) {
        viewModel.popularMovies.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    binding.pbPopular.visibility = View.GONE
                    result.data?.let {
                        popularListAdapter = PopularListAdapter(requireActivity(), it)
                        this.binding.rvPopularMovies.adapter = popularListAdapter
                        popularListAdapter.onClickItem = ::onPopularMovies

                    }
                }

                Result.Status.LOADING -> binding.pbPopular.visibility = View.VISIBLE

                Result.Status.ERROR -> {
                    binding.pbPopular.visibility = View.GONE
                    Snackbar.make(binding.root, result.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })

        this.binding.rvPopularMovies.setLayoutManager(
            LinearLayoutManager(
                activity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )
        this.binding.rvPopularMovies.isNestedScrollingEnabled = false;
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(this.binding.rvPopularMovies)
    }

    // Action movies API calls and Adapter
    private fun initAction(binding: FragmentMoviesBinding) {
        viewModel.actionMovies.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    binding.pbAction.visibility = View.GONE
                    result.data?.let {
                        actionListAdapter = ActionListAdapter(requireActivity(), it)
                        this.binding.rvActionMovies.adapter = actionListAdapter
                        actionListAdapter.onClickItem = ::onActionMovies
                    }
                }

                Result.Status.LOADING -> binding.pbAction.visibility = View.VISIBLE

                Result.Status.ERROR -> {
                    binding.pbAction.visibility = View.GONE
                    Snackbar.make(binding.root, result.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })

        this.binding.rvActionMovies.setLayoutManager(
            LinearLayoutManager(
                activity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )
        this.binding.rvActionMovies.isNestedScrollingEnabled = false;
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(this.binding.rvActionMovies)
    }

    private val runnable = Runnable {
        viewpager_NowPlaying.currentItem = viewpager_NowPlaying.currentItem + 1
    }

    // Now Playing Movies Onclick Direction
    private fun onNowPlayingMovies(id: Int) {
        val direction = MoviesFragmentDirections.moviesListFragmentTodetailsFragment(id, true)
        findNavController().navigate(direction)
    }

    // Popular Movies Onclick Direction
    private fun onPopularMovies(id: Int) {
        val popularMovieDirection =
            MoviesFragmentDirections.moviesListFragmentTodetailsFragment(id, true)
        findNavController().navigate(popularMovieDirection)
    }

    // Action Movies Onclick Direction
    private fun onActionMovies(id: Int) {
        val popularMovieDirection =
            MoviesFragmentDirections.moviesListFragmentTodetailsFragment(id, true)
        findNavController().navigate(popularMovieDirection)
    }

    override fun onPause() {
        super.onPause()
//        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()
//        handler.postDelayed(runnable, 2000)
    }
}