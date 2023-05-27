package com.example.moviesdataapp.ui.fragment

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.moviesdataapp.R
import com.example.moviesdataapp.data.api.Result
import com.example.moviesdataapp.data.model.BackdropImages
import com.example.moviesdataapp.databinding.FragmentTvSeasonBinding
import com.example.moviesdataapp.di.component.Injectable
import com.example.moviesdataapp.di.component.injectViewModel
import com.example.moviesdataapp.ui.adapter.*
import com.example.moviesdataapp.ui.viewmodel.SeasonDetailsViewModel
import com.example.moviesdataapp.utils.ImagePaths
import com.example.moviesdataapp.utils.loadImage
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class TvSeasonFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var binding: FragmentTvSeasonBinding
    lateinit var seasonViewModel: SeasonDetailsViewModel
    private val navArgs: TvSeasonFragmentArgs by navArgs()

    private var seasonID: Int? = null
    private var tvID: Int? = null
    private var seriesName: String? = null
    private var seasonPoster: String? = null

    private lateinit var handler: Handler

    private lateinit var episodeViewpager: ViewPager2
    private lateinit var seasonImageViewpager: ViewPager2

    private lateinit var episodeAdapter: EpisodeAdapter
    private lateinit var seriesCastListAdapter: SeriesCastListAdapter
    private lateinit var seriesCrewListAdapter: SeriesCrewListAdapter
    private lateinit var seasonImagesAdapter: SeasonImagesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tv_season, container, false)

        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        binding.lifecycleOwner = viewLifecycleOwner

        seasonViewModel = injectViewModel(viewModelFactory)

        seasonID = navArgs.seasonId
        tvID = navArgs.tvId
        seriesName = navArgs.seriesName
        seasonPoster = ""

        seasonViewModel.getData(tvID!!, seasonID!!)

        initAPI(binding)

        //Viewpager2 callback
        episodeViewpager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }
        })

        //Viewpager2 callback
        seasonImageViewpager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, 2000)
            }
        })

        return binding.root
    }

    private fun initAPI(binding: FragmentTvSeasonBinding) {

        // Season Details API and Adapter
        seasonViewModel.seasonDetails.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    binding.pbEpisode.visibility = View.GONE

                    result.data?.let {
                        var isShow = true
                        var scrollRange = -1
                        binding.appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { barLayout, verticalOffset ->
                            if (scrollRange == -1) {
                                scrollRange = barLayout?.totalScrollRange!!
                            }
                            if (scrollRange + verticalOffset == 0) {
                                binding.ivPosterPic.visibility = View.GONE
                                binding.cvPosterPic.visibility = View.GONE
                                binding.collapsingToolbar.isTitleEnabled = true
                                binding.collapsingToolbar.title =
                                    seriesName + " " + "(" + it.name + ")"
                                isShow = true
                            } else if (isShow) {
                                binding.collapsingToolbar.title =
                                    " " //careful there should a space between double quote otherwise it wont work
                                isShow = false
                                binding.ivPosterPic.visibility = View.VISIBLE
                                binding.cvPosterPic.visibility = View.VISIBLE
                            }
                        })

                        binding.tvName.text = seriesName
                        binding.tvSeason.text = it.name

                        binding.ivPosterPic.loadImage(
                            it.poster_path,
                            ImagePaths.BACKDROP
                        )

                        // if season images empty, we can use this one
                        seasonPoster = it.poster_path

                        if (it.episodes.size > 0) {
                            binding.tvEpisode.visibility = View.VISIBLE
                            binding.vpEpisodes.visibility = View.VISIBLE

                            episodeAdapter = EpisodeAdapter(it.episodes)
                            episodeViewpager.adapter = episodeAdapter
                            setUpTransformer()
                        } else {
                            binding.tvEpisode.visibility = View.GONE
                            binding.vpEpisodes.visibility = View.GONE
                        }
                    }
                }

                Result.Status.LOADING -> {
                    binding.pbEpisode.visibility = View.VISIBLE
                }

                Result.Status.ERROR -> {
                    binding.pbEpisode.visibility = View.GONE
                    Snackbar.make(binding.root, result.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })

        // Season Cast API and Adapter
        seasonViewModel.seasonCastDetails.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    binding.pbCast.visibility = View.GONE

                    result.data.let {
                        if (it!!.size > 0) {
                            binding.tvLabelCast.visibility = View.VISIBLE
                            binding.rvCast.visibility = View.VISIBLE

                            seriesCastListAdapter = SeriesCastListAdapter(requireContext(), it)
                            binding.rvCast.adapter = seriesCastListAdapter
                            seriesCastListAdapter.onClick = ::onCastClick

                        } else {
                            binding.tvLabelCast.visibility = View.GONE
                            binding.rvCast.visibility = View.GONE
                        }
                    }
                }

                Result.Status.LOADING -> {
                    binding.pbCast.visibility = View.VISIBLE
                }

                Result.Status.ERROR -> {
                    binding.pbCast.visibility = View.GONE
                    Snackbar.make(binding.root, result.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })

        // Season Crew API and Adapter
        seasonViewModel.seasonCrewDetails.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    binding.pbCrew.visibility = View.GONE

                    result.data.let {
                        if (it!!.size > 0) {
                            binding.tvLabelCrew.visibility = View.VISIBLE
                            binding.rvCrew.visibility = View.VISIBLE

                            seriesCrewListAdapter = SeriesCrewListAdapter(requireContext(), it)
                            binding.rvCrew.adapter = seriesCrewListAdapter
                        } else {
                            binding.tvLabelCrew.visibility = View.GONE
                            binding.rvCrew.visibility = View.GONE
                        }
                    }
                }

                Result.Status.LOADING -> {
                    binding.pbCrew.visibility = View.VISIBLE
                }

                Result.Status.ERROR -> {
                    binding.pbCrew.visibility = View.GONE
                    Snackbar.make(binding.root, result.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })

        // Season Images API and Adapter
        seasonViewModel.seasonImages.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    binding.pbSeriesImage.visibility = View.GONE

                    result.data.let {
                        if (it!!.size > 0) {
                            seasonImagesAdapter = SeasonImagesAdapter(it, seasonImageViewpager)
                            seasonImageViewpager.adapter = seasonImagesAdapter
                            seasonImagesAdapter.onClick = ::onSeasonImageClick

                            setUpImageTransformer()
                        } else {
                            binding.ivPosterPic.loadImage(
                                seasonPoster,
                                ImagePaths.BACKDROP
                            )
                        }
                    }
                }

                Result.Status.LOADING -> {
                    binding.pbSeriesImage.visibility = View.VISIBLE
                }

                Result.Status.ERROR -> {
                    binding.pbSeriesImage.visibility = View.GONE
                    Snackbar.make(binding.root, result.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })

        handler = Handler(Looper.myLooper()!!)

        seasonImageViewpager = binding.vpSeriesImages
        episodeViewpager = binding.vpEpisodes
    }

    //Viewpager2 animate
    private fun setUpTransformer() {
        episodeViewpager.apply {
            clipChildren = false  // No clipping the left and right items
            clipToPadding = false  // Show the viewpager in full width without clipping the padding
            offscreenPageLimit = 1  // Render the left and right items
            (getChildAt(0) as RecyclerView).overScrollMode =
                RecyclerView.OVER_SCROLL_NEVER // Remove the scroll effect

            val transformer = CompositePageTransformer()
            transformer.addTransformer(MarginPageTransformer(5))
            transformer.addTransformer { page, position ->
                val r = 1 - kotlin.math.abs(position)
                page.scaleY = 0.85f + r * 0.14f
            }
            episodeViewpager.setPageTransformer(transformer)
        }
    }

    //Viewpager2 animate
    private fun setUpImageTransformer() {
        seasonImageViewpager.apply {
            clipChildren = false  // No clipping the left and right items
            clipToPadding = false  // Show the viewpager in full width without clipping the padding
            offscreenPageLimit = 1  // Render the left and right items
            (getChildAt(0) as RecyclerView).overScrollMode =
                RecyclerView.OVER_SCROLL_NEVER // Remove the scroll effect

            val transformer = CompositePageTransformer()
            transformer.addTransformer(MarginPageTransformer(5))
            transformer.addTransformer { page, position ->
                val r = 1 - kotlin.math.abs(position)
                page.scaleY = 0.85f + r * 0.14f
            }
            seasonImageViewpager.setPageTransformer(transformer)
        }
    }

    // Season Images Click
    private fun onSeasonImageClick(list: List<BackdropImages>, position: Int, isFrom: Boolean) {
        val action = TvSeasonFragmentDirections.actionTvSeasonFragmentToImagePreviewFragment(
            list.toTypedArray(),
            position, isFrom
        )
        findNavController().navigate(action)
    }

    // Cast Click
    private fun onCastClick(id: Int) {
        val action = TvSeasonFragmentDirections.actionTvSeasonFragmentToActorDetailsFragment(id)
        findNavController().navigate(action)
    }

    private val runnable = Runnable {
        seasonImageViewpager.currentItem = seasonImageViewpager.currentItem + 1
    }

}