package com.example.moviesdataapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesdataapp.data.api.Result
import com.example.moviesdataapp.data.model.*
import com.example.moviesdataapp.data.repository.MovieApiRepository
import com.example.moviesdataapp.data.repository.TvApiRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(
    val movieRepository: MovieApiRepository,
    val tvApiRepository: TvApiRepository
) : ViewModel() {

    lateinit var movieDetails: LiveData<Result<MovieDetailsResponse>>
    lateinit var castCrewDetails: LiveData<Result<CastCrewListResponse>>
    lateinit var videosDetails: LiveData<Result<List<MovieVideos>>>
    lateinit var similarMovies: LiveData<Result<List<SimilarMovie>>>
    lateinit var moviesImages: LiveData<Result<List<BackdropImages>>>
    lateinit var moviesReviews: LiveData<Result<List<ReviewList>>>

    lateinit var tvDetails: LiveData<Result<TvDetailsResponse>>
    lateinit var similarTv: LiveData<Result<List<SimilarTv>>>
    lateinit var tvImages: LiveData<Result<List<BackdropImages>>>

    fun getMovieData(movie_id: Int) {
        viewModelScope.launch {
            movieRepository.delete()
            movieRepository.castDelete()
            movieRepository.videosDelete()
            movieRepository.similarMoviesDelete()
            movieRepository.moviesImagesDelete()
            movieRepository.moviesReviewsDelete()

            movieDetails = movieRepository.getMovieAllDetails(movie_id)
            castCrewDetails = movieRepository.getCastCrewDetails(movie_id)
            videosDetails = movieRepository.getMovieVideos(movie_id)
            similarMovies = movieRepository.getSimilarMovies(movie_id)
            moviesImages = movieRepository.getMovieImages(movie_id)
            moviesReviews = movieRepository.getMovieReviews(movie_id)
        }
    }

    fun getTvDetails(tvID: Int) {
        viewModelScope.launch {
            tvApiRepository.deleteTvDetails()
            tvApiRepository.castDelete()
            tvApiRepository.videosDelete()
            tvApiRepository.similarShowsDelete()
            tvApiRepository.tvImagesDelete()

            tvDetails = tvApiRepository.getTvDetails(tvID)
            castCrewDetails = tvApiRepository.getCastCrewDetails(tvID)
            videosDetails = tvApiRepository.getVideos(tvID)
            similarTv = tvApiRepository.getSimilarTv(tvID)
            tvImages = tvApiRepository.getTvImages(tvID)


        }
    }

}