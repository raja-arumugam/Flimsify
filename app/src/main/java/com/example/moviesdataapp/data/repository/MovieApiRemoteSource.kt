package com.example.moviesdataapp.data.repository

import com.example.moviesdataapp.BuildConfig
import com.example.moviesdataapp.data.api.APIService
import com.example.moviesdataapp.data.api.BaseApiDataSource
import javax.inject.Inject

class MovieApiRemoteSource @Inject constructor(
    private val service: APIService
) :
    BaseApiDataSource() {

    suspend fun fetchData() = getResult {
        service.getNowPlayingMovie(BuildConfig.API_KEY)
    }

    suspend fun fetchPopularMovie() = getResult {
        service.getPopularMovies(BuildConfig.API_KEY)
    }

    suspend fun fetchUpcomingMovie() = getResult {
        service.getUpcomingMovie(BuildConfig.API_KEY)
    }


    suspend fun fetchActionMovie() = getResult {
        service.getActionMovies(BuildConfig.API_KEY, 28)
    }

    suspend fun fetchMovieDetails(movieID: Int) = getResult {
        service.getMovieAllDetails(movieID, BuildConfig.API_KEY)
    }

    suspend fun fetchMovieCastCrewDetails(movieID: Int) = getResult {
        service.getMovieCastCrewDetails(movieID, BuildConfig.API_KEY)
    }

    suspend fun fetchActorDetails(personID: Int) = getResult {
        service.getActorDetails(personID, BuildConfig.API_KEY)
    }

    suspend fun fetchActorMovies(personID: Int) = getResult {
        service.getActorMovies(personID, BuildConfig.API_KEY)
    }

    suspend fun fetchMovieVideos(movieID: Int) = getResult {
        service.getMovieVideos(movieID, BuildConfig.API_KEY)
    }

    suspend fun fetchMovieReviews(movieID: Int) = getResult {
        service.getMovieReviews(movieID, BuildConfig.API_KEY)
    }

    suspend fun fetchSimilarMovies(movieID: Int) = getResult {
        service.getSimilarMovies(movieID, BuildConfig.API_KEY)
    }

    suspend fun fetchActorImages(personID: Int) = getResult {
        service.getActorImages(personID, BuildConfig.API_KEY)
    }

    suspend fun fetchMovieImages(movieID: Int) = getResult {
        service.getMovieImages(movieID, BuildConfig.API_KEY)
    }

    suspend fun fetchExternalID(personID: Int) = getResult {
        service.getExternalIds(personID, BuildConfig.API_KEY)
    }

    suspend fun fetchMovieGenreList() = getResult {
        service.getMovieGenreList(BuildConfig.API_KEY)
    }

    suspend fun fetchHollywoodMoviesDiscover(language: String, genres: Int) = getResult {
        service.getHollywoodMoviesDiscover(BuildConfig.API_KEY, language, genres)
    }

    suspend fun fetchKollywoodMoviesDiscover(language: String, genres: Int) = getResult {
        service.getKollywoodMoviesDiscover(BuildConfig.API_KEY, language, genres)
    }

    suspend fun fetchBollywoodMoviesDiscover(language: String, genres: Int) = getResult {
        service.getBollywoodMoviesDiscover(BuildConfig.API_KEY, language, genres)
    }

    suspend fun fetchMollywoodMoviesDiscover(language: String, genres: Int) = getResult {
        service.getMollywoodMoviesDiscover(BuildConfig.API_KEY, language, genres)
    }

    suspend fun fetchKoreanMoviesDiscover(language: String, genres: Int) = getResult {
        service.getKoreanMoviesDiscover(BuildConfig.API_KEY, language, genres)
    }
}