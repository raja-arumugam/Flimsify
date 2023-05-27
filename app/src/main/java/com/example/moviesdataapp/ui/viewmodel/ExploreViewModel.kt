package com.example.moviesdataapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesdataapp.data.api.Result
import com.example.moviesdataapp.data.model.MovieGenreList
import com.example.moviesdataapp.data.repository.MovieApiRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class ExploreViewModel @Inject constructor(
    val movieRepository: MovieApiRepository,
) : ViewModel() {

    val upcomingPlayList = movieRepository.upComingMoviesData
    lateinit var movieGenresList : LiveData<Result<List<MovieGenreList>>>

    fun getData() {
        viewModelScope.launch {
            movieGenresList =  movieRepository.getMovieGenreList()
        }
    }

}