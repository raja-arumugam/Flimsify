package com.example.moviesdataapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.moviesdataapp.data.repository.MovieApiRepository
import javax.inject.Inject

class MoviesListViewModel @Inject constructor(val repository: MovieApiRepository) : ViewModel() {

    val nowPlayingList = repository.nowPlayingMoviesData
    val popularMovies = repository.popularMoviesData
    val actionMovies = repository.actionMoviesData

    /* fun delete() = CoroutineScope(Dispatchers.IO).launch {
             repository.delete()
         }*/
}