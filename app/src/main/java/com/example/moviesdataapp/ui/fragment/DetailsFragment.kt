package com.example.moviesdataapp.ui.fragment

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.moviesdataapp.R
import com.example.moviesdataapp.data.api.Result
import com.example.moviesdataapp.data.model.BackdropImages
import com.example.moviesdataapp.databinding.FragmentDetailsBinding
import com.example.moviesdataapp.di.component.Injectable
import com.example.moviesdataapp.di.component.injectViewModel
import com.example.moviesdataapp.ui.adapter.*
import com.example.moviesdataapp.ui.viewmodel.MovieDetailsViewModel
import com.example.moviesdataapp.utils.ImagePaths
import com.example.moviesdataapp.utils.format
import com.example.moviesdataapp.utils.loadImage
import com.example.moviesdataapp.utils.setLanguage
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DetailsFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var movieDetailsViewModel: MovieDetailsViewModel
    private val navArgs: DetailsFragmentArgs by navArgs()

    private lateinit var castListAdapter: CastListAdapter
    private lateinit var crewListAdapter: CrewListAdapter
    private lateinit var genresListAdapter: GenresListAdapter
    private lateinit var productionCompaniesListAdapter: ProductionCompaniesListAdapter
    private lateinit var movieVideosListAdapter: MovieVideosListAdapter
    private lateinit var similarMoviesAdapter: SimilarMoviesAdapter
    private lateinit var similarTvAdapter: SimilarTvAdapter
    private lateinit var seasonsAdapter: SeasonsAdapter
    private lateinit var backDropImagesAdapter: BackDropImagesAdapter
    private lateinit var reviewsAdapter: ReviewsAdapter

    private lateinit var viewpagerVideos: ViewPager2
    private lateinit var viewpagerSimilar: ViewPager2
    private lateinit var viewpagerSeasons: ViewPager2
    private lateinit var viewpagerBackDropImags: ViewPager2

    private var ID: Int? = null
    private var imgPath: String? = ""
    private var isFromMovie: Boolean = false
    private lateinit var handler: Handler

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)

        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        binding.lifecycleOwner = viewLifecycleOwner

        movieDetailsViewModel = injectViewModel(viewModelFactory)

        ID = navArgs.id
        isFromMovie = navArgs.isFromMovie

        if (isFromMovie) {
            movieDetailsViewModel.getMovieData(ID!!)
            initMovieAPI(binding)
        } else {
            movieDetailsViewModel.getTvDetails(ID!!)
            initTvAPI(binding)
        }

        //Viewpager2 callback
        viewpagerVideos.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }
        })

        viewpagerSimilar.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }
        })

        viewpagerSeasons.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }
        })

        viewpagerBackDropImags.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, 2000)
            }
        })

        return binding.root
    }

    // Movie Details API Response
    private fun initMovieAPI(binding: FragmentDetailsBinding) {

        movieDetailsViewModel.movieDetails.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    result.data?.let {
                        var isShow = true
                        var scrollRange = -1
                        binding.appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { barLayout, verticalOffset ->
                            if (scrollRange == -1) {
                                scrollRange = barLayout?.totalScrollRange!!
                            }
                            if (scrollRange + verticalOffset == 0) {
                                binding.ivMoviePic.visibility = View.GONE
                                binding.cvMoviePic.visibility = View.GONE
                                binding.collapsingToolbar.isTitleEnabled = true
                                binding.collapsingToolbar.title = it.original_title
                                isShow = true
                            } else if (isShow) {
                                binding.collapsingToolbar.title =
                                    " " //careful there should a space between double quote otherwise it wont work
                                isShow = false
                                binding.ivMoviePic.visibility = View.VISIBLE
                                binding.cvMoviePic.visibility = View.VISIBLE
                            }
                        })

                        if (!it.adult) {
                            binding.tvAgeLimit.text = "5+"
                        } else {
                            binding.tvAgeLimit.text = "18+"
                        }

                        binding.tvLanguage.text = this.context!!.setLanguage(it.original_language)

                        val vote = it.vote_average!!.format(1)
                        binding.fbMovie.text = "$vote%"

                        val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
                        val outputFormat: DateFormat = SimpleDateFormat("dd-MMM-yyyy")
                        val inputDateStr = it.release_date
                        val date: Date = inputFormat.parse(inputDateStr) as Date
                        val finalReleaseDate: String = outputFormat.format(date)
                        binding.tvReleaseDate.text = finalReleaseDate

                        binding.tvMovieDesc.text = it.overview
                        binding.tvDuration.text = formatHoursAndMinutes(it.runtime)
                        binding.pbSeasons.visibility = View.GONE

                        if (it.genres.isNotEmpty()) {
                            binding.pbGenres.visibility = View.GONE
                            genresListAdapter = GenresListAdapter(it.genres)
                            binding.rvGenres.adapter = genresListAdapter
                        }

                        if (it.production_companies.isNotEmpty()) {
                            for (i in 0 until it.production_companies.size) {
                                if (it.production_companies[i].logo_path.isNullOrEmpty()) {
                                    binding.pbCompanies.visibility = View.GONE
                                    binding.tvCompanies.visibility = View.GONE
                                    binding.rvProductionCompanies.visibility = View.GONE
                                } else {
                                    binding.pbCompanies.visibility = View.GONE
                                    binding.tvCompanies.visibility = View.VISIBLE
                                    binding.rvProductionCompanies.visibility = View.VISIBLE
                                    productionCompaniesListAdapter = ProductionCompaniesListAdapter(
                                        requireContext(),
                                        it.production_companies
                                    )
                                    binding.rvProductionCompanies.adapter =
                                        productionCompaniesListAdapter
                                }
                            }
                        } else {
                            binding.pbCompanies.visibility = View.GONE
                            binding.tvCompanies.visibility = View.GONE
                            binding.rvProductionCompanies.visibility = View.GONE
                        }

                        binding.ivMoviePic.loadImage(
                            it.poster_path,
                            ImagePaths.POSTER
                        )

                        imgPath = it.poster_path

                    }
                }

                Result.Status.LOADING -> {
                    binding.pbGenres.visibility = View.VISIBLE
                    binding.pbCompanies.visibility = View.VISIBLE
                }

                Result.Status.ERROR -> {
                    binding.pbGenres.visibility = View.GONE
                    binding.pbCompanies.visibility = View.GONE
                    binding.pbSeasons.visibility = View.GONE
                    Snackbar.make(binding.root, result.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })

        // Cast and Crew Details API response
        movieDetailsViewModel.castCrewDetails.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    binding.pbCast.visibility = View.GONE
                    binding.pbCrew.visibility = View.GONE
                    result.data?.let {
                        if (it.cast.isNotEmpty()) {
                            castListAdapter = CastListAdapter(requireContext(), it.cast)
                            binding.rvCast.adapter = castListAdapter
                            castListAdapter.onClick = ::onCastClick
                        }

                        if (it.crew.isNotEmpty()) {
                            crewListAdapter = CrewListAdapter(requireContext(), it.crew)
                            binding.rvCrew.adapter = crewListAdapter
                        }
                    }
                }

                Result.Status.LOADING -> {
                    binding.pbCast.visibility = View.VISIBLE
                    binding.pbCrew.visibility = View.VISIBLE
                }

                Result.Status.ERROR -> {
                    binding.pbCast.visibility = View.GONE
                    binding.pbCrew.visibility = View.GONE
                    Snackbar.make(binding.root, result.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })

        // Movie Videos List
        movieDetailsViewModel.videosDetails.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    binding.pbVideos.visibility = View.GONE
                    result.data.let {
                        if (it!!.size > 0) {
                            binding.tvVideos.visibility = View.VISIBLE
                            binding.vpMoviesVideos.visibility = View.VISIBLE

                            movieVideosListAdapter =
                                MovieVideosListAdapter(it)
                            viewpagerVideos.adapter = movieVideosListAdapter
                            movieVideosListAdapter.onClick = ::onVideosClick

                            setUpTransformer()
                        } else {
                            binding.tvVideos.visibility = View.GONE
                            binding.vpMoviesVideos.visibility = View.GONE
                        }
                    }
                }

                Result.Status.LOADING -> {
                    binding.pbVideos.visibility = View.VISIBLE
                }

                Result.Status.ERROR -> {
                    binding.pbVideos.visibility = View.GONE
                    Snackbar.make(binding.root, result.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })

        // Similar Movies List
        movieDetailsViewModel.similarMovies.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    binding.pbSimilar.visibility = View.GONE

                    result.data.let {
                        if (it!!.size > 0) {
                            binding.tvSimilar.visibility = View.VISIBLE
                            binding.vpSimilarMovieList.visibility = View.VISIBLE
                            binding.pbSimilar.visibility = View.GONE

                            similarMoviesAdapter = SimilarMoviesAdapter(it)
                            viewpagerSimilar.adapter = similarMoviesAdapter
                            similarMoviesAdapter.onClick = ::onSimilarMovies

                            setUpTransformerSimilar()
                        } else {
                            binding.tvSimilar.visibility = View.GONE
                            binding.vpSimilarMovieList.visibility = View.GONE
                            binding.pbSimilar.visibility = View.GONE
                        }
                    }
                }

                Result.Status.LOADING -> {
                    binding.pbSimilar.visibility = View.VISIBLE
                }

                Result.Status.ERROR -> {
                    binding.pbSimilar.visibility = View.GONE
                    Snackbar.make(binding.root, result.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })

        //BackDrops Images API
        movieDetailsViewModel.moviesImages.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    binding.pbBackdropImages.visibility = View.GONE

                    result.data.let {
                        if (it!!.size > 0) {
                            binding.imagBackdrop.visibility = View.GONE
                            binding.vpBackdropImages.visibility = View.VISIBLE

                            backDropImagesAdapter =
                                BackDropImagesAdapter(it, viewpagerBackDropImags)
                            viewpagerBackDropImags.adapter = backDropImagesAdapter
                            backDropImagesAdapter.onClick = ::onBackDropImages

                            setUpTransformerBackDropImages()
                        } else {
                            binding.vpBackdropImages.visibility = View.GONE
                            binding.imagBackdrop.visibility = View.VISIBLE

                            binding.ivMoviePic.loadImage(
                                imgPath,
                                ImagePaths.POSTER
                            )
                        }
                    }
                }

                Result.Status.LOADING -> {
                    binding.pbBackdropImages.visibility = View.VISIBLE
                }

                Result.Status.ERROR -> {
                    binding.pbBackdropImages.visibility = View.GONE
                    Snackbar.make(binding.root, result.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })

        // Movie Reviews API
        movieDetailsViewModel.moviesReviews.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    binding.pbReviews.visibility = View.GONE

                    result.data.let {
                        if (it!!.isNotEmpty()) {
                            binding.tvReviews.visibility = View.VISIBLE
                            binding.rvReviews.visibility = View.VISIBLE

                            reviewsAdapter = ReviewsAdapter(requireContext(), it)
                            binding.rvReviews.adapter = reviewsAdapter

                            binding.rvReviews.onFlingListener = null;
                            val snapHelper: SnapHelper = LinearSnapHelper()
                            snapHelper.attachToRecyclerView(binding.rvReviews)

                        } else {
                            binding.tvReviews.visibility = View.GONE
                            binding.rvReviews.visibility = View.GONE
                        }
                    }
                }

                Result.Status.LOADING -> {
                    binding.pbReviews.visibility = View.VISIBLE
                }

                Result.Status.ERROR -> {
                    binding.pbReviews.visibility = View.GONE
                    Snackbar.make(binding.root, result.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })

        handler = Handler(Looper.myLooper()!!)

        // Viewpagers Initiate
        viewpagerBackDropImags = binding.vpBackdropImages
        viewpagerVideos = binding.vpMoviesVideos
        viewpagerSimilar = binding.vpSimilarMovieList
        viewpagerSeasons = binding.vpSeasonsList

        ID = null
        imgPath = ""
    }

    private fun initTvAPI(binding: FragmentDetailsBinding) {

        // TV details response and Seasons API List
        movieDetailsViewModel.tvDetails.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    result.data.let {
                        if (it != null) {
                            var isShow = true
                            var scrollRange = -1
                            binding.appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { barLayout, verticalOffset ->
                                if (scrollRange == -1) {
                                    scrollRange = barLayout?.totalScrollRange!!
                                }
                                if (scrollRange + verticalOffset == 0) {
                                    binding.ivMoviePic.visibility = View.GONE
                                    binding.cvMoviePic.visibility = View.GONE
                                    binding.collapsingToolbar.isTitleEnabled = true
                                    binding.collapsingToolbar.title = it.name
                                    isShow = true
                                } else if (isShow) {
                                    binding.collapsingToolbar.title =
                                        " " //careful there should a space between double quote otherwise it wont work
                                    isShow = false
                                    binding.ivMoviePic.visibility = View.VISIBLE
                                    binding.cvMoviePic.visibility = View.VISIBLE
                                }
                            })

                            binding.pbSeasons.visibility = View.GONE
                            binding.pbReviews.visibility = View.GONE

                            val vote = it.vote_average.format(1)
                            binding.fbMovie.text = "$vote%"

                            if (it.first_air_date != "" || it.first_air_date.isNotEmpty()) {
                                binding.tvReleaseDate.visibility = View.VISIBLE
                                binding.cvReleaseDate.visibility = View.VISIBLE

                                val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
                                val outputFormat: DateFormat = SimpleDateFormat("dd-MMM-yyyy")
                                val inputDateStr = it.first_air_date
                                val date: Date = inputFormat.parse(inputDateStr) as Date
                                val finalReleaseDate: String = outputFormat.format(date)
                                binding.tvReleaseDate.text = finalReleaseDate
                            } else {
                                binding.tvReleaseDate.visibility = View.GONE
                                binding.cvReleaseDate.visibility = View.GONE
                            }

                            if (it.overview.isNullOrEmpty()) {
                                binding.tvMovieDesc.text = "No data available"
                            } else {
                                binding.tvMovieDesc.text = it.overview
                            }

                            if (!it.adult) {
                                binding.tvAgeLimit.text = "5+"
                            } else {
                                binding.tvAgeLimit.text = "18+"
                            }

                            if (it.original_language.isEmpty()) {
                                binding.cvLanguage.visibility = View.GONE
                                binding.tvLanguage.visibility = View.GONE
                            } else {
                                binding.tvLanguage.text = this.context!!.setLanguage(it.original_language)
                            }

                            if (it.episode_run_time.isEmpty()) {
                                binding.cvDuration.visibility = View.GONE
                                binding.tvDuration.visibility = View.GONE
                            } else {
                                var runTime = ""
                                for (i in it.episode_run_time) {
                                    runTime = it.episode_run_time[0]
                                }
                                binding.cvDuration.visibility = View.VISIBLE
                                binding.tvDuration.visibility = View.VISIBLE
                                binding.tvDuration.text = formatHoursAndMinutes(runTime.toInt())
                            }

                            if (it.genres.isNotEmpty()) {
                                binding.pbGenres.visibility = View.GONE
                                genresListAdapter = GenresListAdapter(it.genres)
                                binding.rvGenres.adapter = genresListAdapter
                            }

                            if (it.production_companies.isNotEmpty()) {
                                for (i in 0 until it.production_companies.size) {
                                    if (it.production_companies[i].logo_path.isNullOrEmpty()) {
                                        binding.pbCompanies.visibility = View.GONE
                                        binding.tvCompanies.visibility = View.GONE
                                        binding.rvProductionCompanies.visibility = View.GONE
                                    } else {
                                        binding.pbCompanies.visibility = View.GONE
                                        binding.tvCompanies.visibility = View.VISIBLE
                                        binding.rvProductionCompanies.visibility = View.VISIBLE
                                        productionCompaniesListAdapter =
                                            ProductionCompaniesListAdapter(
                                                requireContext(),
                                                it.production_companies
                                            )
                                        binding.rvProductionCompanies.adapter =
                                            productionCompaniesListAdapter
                                    }
                                }
                            } else {
                                binding.pbCompanies.visibility = View.GONE
                                binding.tvCompanies.visibility = View.GONE
                                binding.rvProductionCompanies.visibility = View.GONE
                            }

                            binding.ivMoviePic.loadImage(
                                it.poster_path,
                                ImagePaths.POSTER
                            )
                            imgPath = it.poster_path

                            // TV Shows Seasons API
                            if (it!!.seasons.isNotEmpty()) {
                                binding.tvSeason.visibility = View.VISIBLE
                                binding.vpSeasonsList.visibility = View.VISIBLE

                                for (i in 0 until it.seasons.size) {
                                    if (it.seasons[i].poster_path != null) {
                                        seasonsAdapter = SeasonsAdapter(it.seasons, it.id, it.name)
                                        viewpagerSeasons.adapter = seasonsAdapter
                                        seasonsAdapter.onClick = ::onSeasonClick
                                    }
                                }

                                setUpTransformerSeasons()
                            } else {
                                binding.tvSeason.visibility = View.GONE
                                binding.vpSeasonsList.visibility = View.GONE
                            }
                        }
                    }
                }

                Result.Status.LOADING -> {
                    binding.pbGenres.visibility = View.VISIBLE
                    binding.pbCompanies.visibility = View.VISIBLE
                    binding.pbSeasons.visibility = View.VISIBLE
                }

                Result.Status.ERROR -> {
                    binding.pbGenres.visibility = View.GONE
                    binding.pbCompanies.visibility = View.GONE
                    binding.pbSeasons.visibility = View.GONE
                    Snackbar.make(binding.root, result.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })

        // TV Cast and Crew Details API response
        movieDetailsViewModel.castCrewDetails.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    binding.pbCast.visibility = View.GONE
                    binding.pbCrew.visibility = View.GONE

                    result.data?.let {
                        if (it.cast.isNotEmpty()) {
                            castListAdapter = CastListAdapter(requireContext(), it.cast)
                            binding.rvCast.adapter = castListAdapter
                            castListAdapter.onClick = ::onCastClick
                        } else {
                            binding.tvLabelCast.visibility = View.GONE
                            binding.rvCast.visibility = View.GONE
                        }

                        if (it.crew.isNotEmpty()) {
                            crewListAdapter = CrewListAdapter(requireContext(), it.crew)
                            binding.rvCrew.adapter = crewListAdapter
                        } else {
                            binding.tvLabelCrew.visibility = View.GONE
                            binding.rvCrew.visibility = View.GONE
                        }
                    }
                }

                Result.Status.LOADING -> {
                    binding.pbCast.visibility = View.VISIBLE
                    binding.pbCrew.visibility = View.VISIBLE
                }

                Result.Status.ERROR -> {
                    binding.pbCast.visibility = View.GONE
                    binding.pbCrew.visibility = View.GONE
                    Snackbar.make(binding.root, result.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })

        // TV Videos List
        movieDetailsViewModel.videosDetails.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    binding.pbVideos.visibility = View.GONE
                    result.data.let {
                        if (it!!.size > 0) {
                            binding.tvVideos.visibility = View.VISIBLE
                            binding.vpMoviesVideos.visibility = View.VISIBLE

                            movieVideosListAdapter =
                                MovieVideosListAdapter(it)
                            viewpagerVideos.adapter = movieVideosListAdapter
                            movieVideosListAdapter.onClick = ::onVideosClick

                            setUpTransformer()
                        } else {
                            binding.tvVideos.visibility = View.GONE
                            binding.vpMoviesVideos.visibility = View.GONE
                        }
                    }
                }

                Result.Status.LOADING -> {
                    binding.pbVideos.visibility = View.VISIBLE
                }

                Result.Status.ERROR -> {
                    binding.pbVideos.visibility = View.GONE
                    Snackbar.make(binding.root, result.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })

        // Tv Similar API
        movieDetailsViewModel.similarTv.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    binding.pbSimilar.visibility = View.GONE

                    result.data.let {
                        if (it!!.size > 0) {
                            binding.tvSimilar.visibility = View.VISIBLE
                            binding.vpSimilarMovieList.visibility = View.VISIBLE

                            similarTvAdapter = SimilarTvAdapter(it)
                            viewpagerSimilar.adapter = similarTvAdapter
                            similarTvAdapter.onClick = ::onSimilarTV

                            setUpTransformerSimilar()
                        } else {
                            binding.tvSimilar.visibility = View.GONE
                            binding.vpSimilarMovieList.visibility = View.GONE
                        }
                    }
                }

                Result.Status.LOADING -> {
                    binding.pbSimilar.visibility = View.VISIBLE
                }

                Result.Status.ERROR -> {
                    binding.pbSimilar.visibility = View.GONE
                    Snackbar.make(binding.root, result.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })

        //BackDrops Images API
        movieDetailsViewModel.tvImages.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    binding.pbBackdropImages.visibility = View.GONE

                    result.data.let {
                        if (it!!.size > 0) {
                            binding.imagBackdrop.visibility = View.GONE
                            binding.vpBackdropImages.visibility = View.VISIBLE

                            backDropImagesAdapter =
                                BackDropImagesAdapter(it, viewpagerBackDropImags)
                            viewpagerBackDropImags.adapter = backDropImagesAdapter
                            backDropImagesAdapter.onClick = ::onBackDropImages

                            setUpTransformerBackDropImages()
                        } else {
                            binding.vpBackdropImages.visibility = View.GONE
                            binding.imagBackdrop.visibility = View.VISIBLE

                            binding.ivMoviePic.loadImage(
                                imgPath,
                                ImagePaths.POSTER
                            )
                        }
                    }
                }

                Result.Status.LOADING -> {
                    binding.pbBackdropImages.visibility = View.VISIBLE
                }

                Result.Status.ERROR -> {
                    binding.pbBackdropImages.visibility = View.GONE
                    Snackbar.make(binding.root, result.message!!, Snackbar.LENGTH_LONG).show()
                }

            }
        })

        // ViewPager's Initiate
        viewpagerBackDropImags = binding.vpBackdropImages
        viewpagerVideos = binding.vpMoviesVideos
        viewpagerSimilar = binding.vpSimilarMovieList
        viewpagerSeasons = binding.vpSeasonsList

        handler = Handler(Looper.myLooper()!!)
        ID = null
        imgPath = ""
    }

    // Similar Movie Click
    private fun onSimilarMovies(id: Int) {
        movieDetailsViewModel.getMovieData(id)
        initMovieAPI(binding)
    }

    // Similar TV Click
    private fun onSimilarTV(id: Int) {
        movieDetailsViewModel.getTvDetails(id)
        initTvAPI(binding)
    }

    // Cast Click
    private fun onCastClick(id: Int) {
        val direction =
            DetailsFragmentDirections.actionDetailsFragmentToActorDetailsFragment(id)
        findNavController().navigate(direction)
    }

    // Movie Videos Click
    private fun onVideosClick(videoKey: String) {
        val action = DetailsFragmentDirections.actionDetailsFragmentToVideoPlayerFragment(videoKey)
        findNavController().navigate(action)
    }

    // TV Seasons Click
    private fun onSeasonClick(seasonNumber: Int, TvID: Int, seriesName: String) {
        val action =
            DetailsFragmentDirections.actionDetailsFragmentToTvSeasonFragment(
                seasonNumber,
                TvID,
                seriesName
            )
        findNavController().navigate(action)
    }

    // Back Drop Images Click
    private fun onBackDropImages(list: List<BackdropImages>, position: Int, isFrom: Boolean) {
        val action =
            DetailsFragmentDirections.actionDetailsFragmentToImagePreviewFragment(
                list.toTypedArray(), position, isFrom
            )
        findNavController().navigate(action)
    }

    private fun formatHoursAndMinutes(totalMinutes: Int): String {
        var minutes = Integer.toString(totalMinutes % 60)
        minutes = if (minutes.length == 1) "0$minutes" else minutes
        return (totalMinutes / 60).toString() + "h " + minutes + "m"
    }

    //Viewpager2 animate
    private fun setUpTransformer() {
        viewpagerVideos.apply {
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

            viewpagerVideos.setPageTransformer(transformer)
        }
    }

    //Viewpager2 animate
    private fun setUpTransformerSimilar() {
        viewpagerSimilar.apply {
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

            viewpagerSimilar.setPageTransformer(transformer)
        }
    }

    //Viewpager2 animate
    private fun setUpTransformerSeasons() {
        viewpagerSeasons.apply {
            clipChildren = false  // No clipping the left and right items
            clipToPadding = false  // Show the viewpager in full width without clipping the padding
            offscreenPageLimit = 3  // Render the left and right items
            (getChildAt(0) as RecyclerView).overScrollMode =
                RecyclerView.OVER_SCROLL_NEVER // Remove the scroll effect

            val transformer = CompositePageTransformer()
            transformer.addTransformer(MarginPageTransformer(40))
            transformer.addTransformer { page, position ->
                val r = 1 - kotlin.math.abs(position)
                page.scaleY = 0.85f + r * 0.14f
            }

            viewpagerSeasons.setPageTransformer(transformer)
        }
    }

    //Viewpager2 animate
    private fun setUpTransformerBackDropImages() {
        viewpagerBackDropImags.apply {
            clipChildren = false  // No clipping the left and right items
            clipToPadding = false  // Show the viewpager in full width without clipping the padding
            offscreenPageLimit = 3  // Render the left and right items
            (getChildAt(0) as RecyclerView).overScrollMode =
                RecyclerView.OVER_SCROLL_NEVER // Remove the scroll effect

            val transformer = CompositePageTransformer()
            transformer.addTransformer(MarginPageTransformer(40))
            transformer.addTransformer { page, position ->
                val r = 1 - kotlin.math.abs(position)
                page.scaleY = 0.85f + r * 0.14f
            }

            viewpagerBackDropImags.setPageTransformer(transformer)
        }
    }

    private val runnable = Runnable {
        viewpagerBackDropImags.currentItem = viewpagerBackDropImags.currentItem + 1
    }
}