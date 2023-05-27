package com.example.moviesdataapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesdataapp.data.api.Result
import com.example.moviesdataapp.data.model.BollyWoodMoviesList
import com.example.moviesdataapp.data.model.HollyWoodMoviesList
import com.example.moviesdataapp.data.model.KollyWoodMoviesList
import com.example.moviesdataapp.data.model.KoreanMoviesList
import com.example.moviesdataapp.data.model.MollyWoodMoviesList
import com.example.moviesdataapp.data.repository.MovieApiRepository
import com.example.moviesdataapp.data.repository.TvApiRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class GenresMoviesViewModel @Inject constructor(
    val movieRepository: MovieApiRepository
) : ViewModel() {

    lateinit var hollyWoodMoviesList: LiveData<Result<List<HollyWoodMoviesList>>>
    lateinit var kollyWoodMoviesList: LiveData<Result<List<KollyWoodMoviesList>>>
    lateinit var bollyWoodMoviesList: LiveData<Result<List<BollyWoodMoviesList>>>
    lateinit var mollyWoodMoviesList: LiveData<Result<List<MollyWoodMoviesList>>>
    lateinit var koreanMoviesList: LiveData<Result<List<KoreanMoviesList>>>


    fun getData(genreId: Int) {
        viewModelScope.launch {
            movieRepository.deleteHollyMovies()
            movieRepository.deleteKollyWoodMovies()
            movieRepository.deleteBollyWoodMovies()
            movieRepository.deleteMollyWoodMovies()
            movieRepository.deleteKoreanMovies()

            hollyWoodMoviesList = movieRepository.getHollywoodMoviesDiscover("en", genreId)
            kollyWoodMoviesList = movieRepository.getKollywoodMoviesDiscover("ta", genreId)
            bollyWoodMoviesList = movieRepository.getBollywoodMoviesDiscover("hi", genreId)
            mollyWoodMoviesList = movieRepository.getMollywoodMoviesDiscover("ml", genreId)
            koreanMoviesList = movieRepository.getKoreanMoviesDiscover("ko", genreId)

        }
    }

}