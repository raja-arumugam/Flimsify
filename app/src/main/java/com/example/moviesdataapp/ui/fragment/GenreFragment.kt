package com.example.moviesdataapp.ui.fragment

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.moviesdataapp.R
import com.example.moviesdataapp.data.api.Result
import com.example.moviesdataapp.databinding.FragmentGenreBinding
import com.example.moviesdataapp.di.component.Injectable
import com.example.moviesdataapp.di.component.injectViewModel
import com.example.moviesdataapp.ui.adapter.BollywoodMoviesAdapter
import com.example.moviesdataapp.ui.adapter.HollywoodMoviesAdapter
import com.example.moviesdataapp.ui.adapter.KollywoodMoviesAdapter
import com.example.moviesdataapp.ui.adapter.KoreanMoviesAdapter
import com.example.moviesdataapp.ui.adapter.MollywoodMoviesAdapter
import com.example.moviesdataapp.ui.viewmodel.GenresMoviesViewModel
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class GenreFragment : Fragment(), Injectable, MotionLayout.TransitionListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: FragmentGenreBinding
    private lateinit var viewmodel: GenresMoviesViewModel
    private val navArgs: GenreFragmentArgs by navArgs()

    lateinit var hollywoodMoviesAdapter: HollywoodMoviesAdapter
    lateinit var kollywoodMoviesAdapter: KollywoodMoviesAdapter
    lateinit var bollywoodMoviesAdapter: BollywoodMoviesAdapter
    lateinit var mollywoodMoviesAdapter: MollywoodMoviesAdapter
    lateinit var koreanMoviesAdapter: KoreanMoviesAdapter

    private lateinit var motionLayout: MotionLayout

    var lastProgress = 0f

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_genre, container, false)

        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        binding.lifecycleOwner = viewLifecycleOwner

        viewmodel = injectViewModel(viewModelFactory)

        motionLayout = binding.motionLayout

        navArgs.let {
            viewmodel.getData(it.genreId)
            binding.tvTitle.text = it.name

            Glide.with(requireActivity()).load(it.imgPath)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(R.drawable.gray_placeholder)
                .into(binding.imgBackdrop)

            binding.tvHolly.text = "Hollywood " + it.name
            binding.tvKolly.text = "Kollywood " + it.name
            binding.tvBolly.text = "Bollywood " + it.name
            binding.tvMolly.text = "Mollywood " + it.name
            binding.tvKorean.text = "Korean " + it.name
        }

        initAPI(binding)

        return binding.root
    }

    private fun initAPI(binding: FragmentGenreBinding) {

        //Hollywood Movies API
        viewmodel.hollyWoodMoviesList.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    result.data.let {
                        if (it!!.isNotEmpty()) {
                            hollywoodMoviesAdapter = HollywoodMoviesAdapter(requireActivity(), it)
                            binding.rvHolly.adapter = hollywoodMoviesAdapter
                            hollywoodMoviesAdapter.onClick = ::onHollywoodListClick
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

        this.binding.rvHolly.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        val snapHelperHolly: SnapHelper = LinearSnapHelper()
        snapHelperHolly.attachToRecyclerView(binding.rvHolly)


        //Kollywood Movies API
        viewmodel.kollyWoodMoviesList.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    result.data.let {
                        if (it!!.isNotEmpty()) {
                            kollywoodMoviesAdapter = KollywoodMoviesAdapter(requireActivity(), it)
                            binding.rvKolly.adapter = kollywoodMoviesAdapter
                            kollywoodMoviesAdapter.onClick = ::onKollywoodListClick
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

        this.binding.rvKolly.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        val snapHelperKolly: SnapHelper = LinearSnapHelper()
        snapHelperKolly.attachToRecyclerView(binding.rvKolly)


        //Bollywood Movies API
        viewmodel.bollyWoodMoviesList.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    result.data.let {
                        if (it!!.isNotEmpty()) {
                            bollywoodMoviesAdapter = BollywoodMoviesAdapter(requireActivity(), it)
                            binding.rvBolly.adapter = bollywoodMoviesAdapter
                            bollywoodMoviesAdapter.onClick = ::onBollywoodListClick
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

        this.binding.rvBolly.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        val snapHelperBolly: SnapHelper = LinearSnapHelper()
        snapHelperBolly.attachToRecyclerView(binding.rvBolly)


        //Mollywood Movies API
        viewmodel.mollyWoodMoviesList.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    result.data.let {
                        if (it!!.isNotEmpty()) {
                            mollywoodMoviesAdapter = MollywoodMoviesAdapter(requireActivity(), it)
                            binding.rvMolly.adapter = mollywoodMoviesAdapter
                            mollywoodMoviesAdapter.onClick = ::onMollywoodListClick
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

        this.binding.rvMolly.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        val snapHelperMolly: SnapHelper = LinearSnapHelper()
        snapHelperMolly.attachToRecyclerView(binding.rvMolly)


        //Korean Movies API
        viewmodel.koreanMoviesList.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    result.data.let {
                        if (it!!.isNotEmpty()) {
                            koreanMoviesAdapter = KoreanMoviesAdapter(requireActivity(), it)
                            binding.rvKorean.adapter = koreanMoviesAdapter
                            koreanMoviesAdapter.onClick = ::onKoreanListClick
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

        this.binding.rvKorean.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        val snapHelperKorean: SnapHelper = LinearSnapHelper()
        snapHelperKorean.attachToRecyclerView(binding.rvKorean)

    }

    // Hollywood Adapter List Click
    private fun onHollywoodListClick(id: Int) {
        val direction = GenreFragmentDirections.actionGenreFragmentToDetailsFragment(id, true)
        findNavController().navigate(direction)
    }

    // Kollywood Adapter List Click
    private fun onKollywoodListClick(id: Int) {
        val direction = GenreFragmentDirections.actionGenreFragmentToDetailsFragment(id, true)
        findNavController().navigate(direction)
    }

    // Bollywood Adapter List Click
    private fun onBollywoodListClick(id: Int) {
        val direction = GenreFragmentDirections.actionGenreFragmentToDetailsFragment(id, true)
        findNavController().navigate(direction)
    }

    // Mollywood Adapter List Click
    private fun onMollywoodListClick(id: Int) {
        val direction = GenreFragmentDirections.actionGenreFragmentToDetailsFragment(id, true)
        findNavController().navigate(direction)
    }

    // Korean Adapter List Click
    private fun onKoreanListClick(id: Int) {
        val direction = GenreFragmentDirections.actionGenreFragmentToDetailsFragment(id, true)
        findNavController().navigate(direction)
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

}