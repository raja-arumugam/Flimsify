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
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.moviesdataapp.R
import com.example.moviesdataapp.data.api.Result
import com.example.moviesdataapp.databinding.FragmentTvShowsListBinding
import com.example.moviesdataapp.di.component.Injectable
import com.example.moviesdataapp.di.component.injectViewModel
import com.example.moviesdataapp.ui.adapter.AiringTodayTVListAdapter
import com.example.moviesdataapp.ui.adapter.PopularListTVAdapter
import com.example.moviesdataapp.ui.adapter.TopRatedTVAdapter
import com.example.moviesdataapp.ui.viewmodel.TvListViewModel
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class TvShowsListFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: FragmentTvShowsListBinding
    private lateinit var viewModel: TvListViewModel

    private lateinit var viewpagerTopRatedTV: ViewPager2
//    private lateinit var handler: Handler

    private lateinit var topRatedTvAdapter: TopRatedTVAdapter
    private lateinit var popularListAdapter: PopularListTVAdapter
    private lateinit var airingTodayTVListAdapter: AiringTodayTVListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_tv_shows_list, container, false)

        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

        viewModel = injectViewModel(viewModelFactory)
        binding.lifecycleOwner = viewLifecycleOwner

        // Viewpager2 Trending List
        initTopRated(binding)
        initPopular(binding)
        initAiringToday(binding)

        //Viewpager2 callback
        viewpagerTopRatedTV.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
               /* handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, 2000)*/
            }
        })

        return binding.root
    }

    // TopRated TV shows Adapter
    private fun initTopRated(binding: FragmentTvShowsListBinding) {
        viewModel.tvTopRatedList.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    binding.pbTopRated.visibility = View.GONE
                    result.data?.let {
                        topRatedTvAdapter =
                            TopRatedTVAdapter(requireActivity(), it)
                        viewpagerTopRatedTV.adapter = topRatedTvAdapter
                        topRatedTvAdapter.onClick = ::onTopRatedTV

                        setUpTransformer()
                    }
                }
                Result.Status.LOADING -> binding.pbTopRated.visibility = View.VISIBLE
                Result.Status.ERROR -> {
                    binding.pbTopRated.visibility = View.GONE
                    Snackbar.make(binding.root, result.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })

        viewpagerTopRatedTV = this.binding.vpTopRatedTvlist
        this.binding.root!!.layoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

//        handler = Handler(Looper.myLooper()!!)

        viewpagerTopRatedTV.offscreenPageLimit = 3
        viewpagerTopRatedTV.clipToPadding = false
        viewpagerTopRatedTV.clipChildren = false
        viewpagerTopRatedTV.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
    }

    // Popular Adapter
    fun initPopular(binding: FragmentTvShowsListBinding) {
        viewModel.tvPopularList.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    binding.pbPopular.visibility = View.GONE
                    result.data?.let {
                        popularListAdapter = PopularListTVAdapter(requireActivity(), it)
                        this.binding.rvPopularTv.adapter = popularListAdapter
                        popularListAdapter.onClick = :: onPopularTv
                    }
                }

                Result.Status.LOADING -> binding.pbPopular.visibility = View.VISIBLE

                Result.Status.ERROR -> {
                    binding.pbPopular.visibility = View.GONE
                    Snackbar.make(binding.root, result.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })

        this.binding.rvPopularTv.setLayoutManager(
            LinearLayoutManager(
                activity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )
        this.binding.rvPopularTv.isNestedScrollingEnabled = false;

        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(this.binding.rvPopularTv)
    }

    // AiringToday Adapter
    fun initAiringToday(binding: FragmentTvShowsListBinding) {
        viewModel.tvAiringTodayList.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    binding.pbAiring.visibility = View.GONE
                    result.data?.let {
                        airingTodayTVListAdapter = AiringTodayTVListAdapter(requireActivity(), it)
                        this.binding.rvAiringToday.adapter = airingTodayTVListAdapter
                        airingTodayTVListAdapter.onClick = :: onAiringTV
                    }
                }

                Result.Status.LOADING -> binding.pbAiring.visibility = View.VISIBLE

                Result.Status.ERROR -> {
                    binding.pbAiring.visibility = View.GONE
                    Snackbar.make(binding.root, result.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })

        this.binding.rvAiringToday.setLayoutManager(
            LinearLayoutManager(
                activity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )
        this.binding.rvAiringToday.isNestedScrollingEnabled = false;
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(this.binding.rvAiringToday)
    }

    //Viewpager2 animate
    private fun setUpTransformer() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1 - Math.abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }

        viewpagerTopRatedTV.setPageTransformer(transformer)
    }

    private val runnable = Runnable {
        viewpagerTopRatedTV.currentItem = viewpagerTopRatedTV.currentItem + 1
    }

    // Top Rated TV onClick
    private fun onTopRatedTV(id: Int) {
        val topTvDirection = TvShowsListFragmentDirections.actionTvFragmentToDetailsFragment(id,false)
        findNavController().navigate(topTvDirection)
    }

    // Top Popular TV onClick
    private fun onPopularTv(id: Int) {
        val topTvDirection = TvShowsListFragmentDirections.actionTvFragmentToDetailsFragment(id,false)
        findNavController().navigate(topTvDirection)
    }

    // Top Airing TV onClick
    private fun onAiringTV(id: Int) {
        val topTvDirection = TvShowsListFragmentDirections.actionTvFragmentToDetailsFragment(id,false)
        findNavController().navigate(topTvDirection)
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