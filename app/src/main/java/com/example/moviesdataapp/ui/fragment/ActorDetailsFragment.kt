package com.example.moviesdataapp.ui.fragment

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.moviesdataapp.R
import com.example.moviesdataapp.data.api.Result
import com.example.moviesdataapp.data.model.BackdropImages
import com.example.moviesdataapp.databinding.FragmentActorDetailsBinding
import com.example.moviesdataapp.di.component.Injectable
import com.example.moviesdataapp.di.component.injectViewModel
import com.example.moviesdataapp.ui.adapter.ActorImagesAdapter
import com.example.moviesdataapp.ui.adapter.KnownAsAdapter
import com.example.moviesdataapp.ui.adapter.KnownForListAdapter
import com.example.moviesdataapp.ui.viewmodel.ActorDetailsViewModel
import com.example.moviesdataapp.utils.ImagePaths
import com.example.moviesdataapp.utils.loadImage
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDate
import java.time.Year
import java.time.temporal.ChronoUnit
import javax.inject.Inject

class ActorDetailsFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: FragmentActorDetailsBinding
    private lateinit var viewModel: ActorDetailsViewModel
    private val navArgs: ActorDetailsFragmentArgs by navArgs()

    private lateinit var knownAsAdapter: KnownAsAdapter
    private lateinit var knownForAdapter: KnownForListAdapter
    private lateinit var actorImagesAdapter: ActorImagesAdapter

    private lateinit var actorImageViewPager: ViewPager2

    private var actorID: Int? = null
    private var instaID: String? = null
    private var fbID: String? = null
    private var twitterID: String? = null

    private lateinit var handler: Handler

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_actor_details, container, false)
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

        viewModel = injectViewModel(viewModelFactory)
        binding.lifecycleOwner = viewLifecycleOwner

        actorID = navArgs.id

        viewModel.getData(actorID!!)

        initAPI(binding)

        //Viewpager2 callback
        actorImageViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, 2000)
            }
        })
        return binding.root
    }

    fun initAPI(binding: FragmentActorDetailsBinding) {

        // Actor Details API
        viewModel.actorDetails.observe(viewLifecycleOwner, Observer { result ->
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
                                    binding.ivProfilePic.visibility = View.GONE
                                    binding.cvProfilePic.visibility = View.GONE
                                    binding.collapsingToolbar.isTitleEnabled = true
                                    binding.collapsingToolbar.title = it.name
                                    isShow = true
                                } else if (isShow) {
                                    binding.collapsingToolbar.title =
                                        " " //careful there should a space between double quote otherwise it wont work
                                    isShow = false
                                    binding.ivProfilePic.visibility = View.VISIBLE
                                    binding.cvProfilePic.visibility = View.VISIBLE
                                }
                            })

                            binding.tvName.text = it.name
                            binding.tvField.text = it.known_for_department

                            when (it.gender) {
                                1 -> {
                                    binding.tvGender.text = "Female"
                                }

                                2 -> {
                                    binding.tvGender.text = "Male"
                                }

                                else -> {
                                    binding.tvGender.text = "No Data"
                                }
                            }

                            if (it.biography!!.isEmpty()) {
                                binding.tvBio.text = "No Data"
                            } else {
                                binding.tvBio.text = it.biography
                            }

                            if (it.place_of_birth.isNullOrEmpty()) {
                                binding.tvPlaceOfBirth.text = "No Data"
                            } else {
                                binding.tvPlaceOfBirth.text = it.place_of_birth
                            }

                            if (it.birthday.isNullOrEmpty()) {
                                binding.tvBornOn.text = "No Data"
                            } else {
                                binding.tvBornOn.text = it.birthday
                                ageCalculation(it.birthday!!)
                            }

                            binding.ivProfilePic.loadImage(
                                it.profile_path,
                                ImagePaths.POSTER
                            )

                            if (it.also_known_as.isNotEmpty()) {
                                knownAsAdapter = KnownAsAdapter(it.also_known_as)
                                binding.rvKnownAs.adapter = knownAsAdapter
                            }
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

        // Actor's External ID's
        viewModel.externalIDs.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    result.data.let {
                        if (it != null) {
                            instaID = it.instagram_id
                            fbID = it.facebook_id
                            twitterID = it.twitter_id

                            binding.llInsta.setOnClickListener {
                                if (instaID.isNullOrEmpty()) {
                                    Snackbar.make(
                                        binding.root,
                                        "No User Data",
                                        Snackbar.LENGTH_LONG
                                    )
                                        .show()
                                } else {
                                    openInstagram(instaID!!)
                                }
                            }

                            binding.llFb.setOnClickListener {
                                if (fbID.isNullOrEmpty()) {
                                    Snackbar.make(
                                        binding.root,
                                        "No User Data",
                                        Snackbar.LENGTH_LONG
                                    )
                                        .show()
                                } else {
                                    openFacebook(fbID!!)
                                }
                            }

                            binding.llTwr.setOnClickListener {
                                if (twitterID.isNullOrEmpty()) {
                                    Snackbar.make(
                                        binding.root,
                                        "No User Data",
                                        Snackbar.LENGTH_LONG
                                    )
                                        .show()
                                } else {
                                    openTwitter(twitterID!!)
                                }
                            }

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

        // Actor Movies List
        viewModel.actorMovies.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    result.data.let {
                        if (it != null) {
                            binding.pbMoviesList.visibility = View.GONE
                            knownForAdapter = KnownForListAdapter(requireContext(), it)
                            binding.rvPersonMovies.adapter = knownForAdapter
                            knownForAdapter.onClick = ::onKnownForClick
                        }
                    }
                }

                Result.Status.LOADING -> {
                    binding.pbMoviesList.visibility = View.VISIBLE
                }

                Result.Status.ERROR -> {
                    binding.pbMoviesList.visibility = View.GONE
                    Snackbar.make(binding.root, result.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })

        this.binding.rvPersonMovies.setLayoutManager(
            LinearLayoutManager(
                activity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )
        this.binding.rvPersonMovies.isNestedScrollingEnabled = false;
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(this.binding.rvPersonMovies)

        // Actor's Images List
        viewModel.imagesList.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    binding.pbActorImageList.visibility = View.GONE
                    result.data.let {
                        if (it != null) {
                            actorImagesAdapter =
                                ActorImagesAdapter(it, actorImageViewPager)
                            actorImageViewPager.adapter = actorImagesAdapter
                            actorImagesAdapter.onClick = ::onActorImagesClick
                            setUpTransformer()
                        }
                    }
                }

                Result.Status.LOADING -> {
                    binding.pbActorImageList.visibility = View.VISIBLE
                }

                Result.Status.ERROR -> {
                    binding.pbActorImageList.visibility = View.GONE
                    Snackbar.make(binding.root, result.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })

        actorImageViewPager = binding.vpActorImages
        binding.root!!.layoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

        actorImageViewPager.offscreenPageLimit = 3
        actorImageViewPager.clipToPadding = false
        actorImageViewPager.clipChildren = false
        actorImageViewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        handler = Handler(Looper.myLooper()!!)

    }

    //Viewpager2 animate
    private fun setUpTransformer() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1 - Math.abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }
        actorImageViewPager.setPageTransformer(transformer)
    }

    private val runnable = Runnable {
        actorImageViewPager.currentItem = actorImageViewPager.currentItem + 1
    }

    // Actor Images Click
    private fun onActorImagesClick(list: List<BackdropImages>, position: Int, isFrom: Boolean) {
        val action =
            ActorDetailsFragmentDirections.actionActorDetailsFragmentToImagePreviewFragment(
                list.toTypedArray(),
                position, isFrom
            )
        findNavController().navigate(action)
    }

    // Actor KnownFor Click
    private fun onKnownForClick(id: Int) {
        val action =
            ActorDetailsFragmentDirections.actionActorDetailsFragmentToDetailsFragment(id, true)
        findNavController().navigate(action)
    }

    // Age Calculation by Birth Year
    private fun ageCalculation(birth: String?) {
        if (birth.isNullOrBlank()) {
            binding.tvAge.text = "No Data"
        } else {
            // Split Birthday
            val birthday = birth
            val birthYear = birthday.split("-")[0]
            val birthMonth = birthday.split("-")[1]
            val day = birthday.split("-")[2]

            // Calculate Age
            val today: LocalDate = LocalDate.now()
            val birth_Day =
                LocalDate.of(birthYear.toInt(), birthMonth.toInt(), day.toInt())
            val thisYearsBirthday: LocalDate = birth_Day.with(
                Year.now()
            )

            val age = ChronoUnit.YEARS.between(birth_Day, today)

            if (thisYearsBirthday == today) {
                System.out.println("It is your birthday, and your Age is $age")
            }
            binding.tvAge.text = "$age Years old"
        }
    }


    // Instagram
    private fun openInstagram(name: String) {
        val appUri: Uri = Uri.parse("https://instagram.com/_u/$name")
        val browserUri: Uri = Uri.parse("https://instagram.com/$name")

        try {
            val appIntent: Intent =
                activity!!.packageManager.getLaunchIntentForPackage("com.instagram.android")!!

            if (appIntent != null) {
                appIntent.action = Intent.ACTION_VIEW
                appIntent.data = appUri;
                startActivity(appIntent);
            }

        } catch (e: Exception) {
            val browserIntent = Intent(Intent.ACTION_VIEW, browserUri)
            startActivity(browserIntent)
        }
    }

    // Facebook
    private fun openFacebook(id: String?) {
        val appFb = "fb://page/$id"
        val urlFb = "https://www.facebook.com/$id"

        try {
            if (isAppInstalled(context!!, "com.facebook.katana")) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(appFb)))
            } else {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(urlFb)))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //Twitter
    private fun openTwitter(id: String?) {
        val urlTw = "https://twitter.com/$id"

        try {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("twitter://user?screen_name=$urlTw")
                )
            )
        } catch (e: Exception) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://twitter.com/#!/$urlTw")
                )
            )

        }

    }

    fun isAppInstalled(context: Context, packageName: String?): Boolean {
        return try {
            context.packageManager.getApplicationInfo(packageName!!, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }
}