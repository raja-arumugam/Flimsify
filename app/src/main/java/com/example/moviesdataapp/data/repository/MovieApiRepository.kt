package com.example.moviesdataapp.data.repository

import androidx.lifecycle.LiveData
import com.example.moviesdataapp.data.api.Result
import com.example.moviesdataapp.data.api.resultLiveData
import com.example.moviesdataapp.data.api.resultsLiveData
import com.example.moviesdataapp.data.local.MovieDao
import com.example.moviesdataapp.data.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieApiRepository @Inject constructor(
    private val dao: MovieDao,
    private val remoteSource: MovieApiRemoteSource,
) {

    val nowPlayingMoviesData = resultLiveData(
        networkCall = { remoteSource.fetchData() },
        saveCallResult = { dao.insertAllNowPlaying(it.results) },
        databaseQuery = { dao.getNowPlaying() }
    )

    val popularMoviesData = resultLiveData(
        networkCall = { remoteSource.fetchPopularMovie() },
        saveCallResult = { dao.insertAllPopularMovies(it.results) },
        databaseQuery = { dao.getPopularMovies() }
    )

    val upComingMoviesData = resultLiveData(
        networkCall = { remoteSource.fetchUpcomingMovie() },
        saveCallResult = { dao.insertAllUpcomingMovies(it.results) },
        databaseQuery = { dao.getUpcomingMovies() }
    )


    val actionMoviesData = resultLiveData(
        networkCall = { remoteSource.fetchActionMovie() },
        saveCallResult = { dao.insertAllActionMovies(it.results) },
        databaseQuery = { dao.getActionMovies() }
    )

    suspend fun getMovieAllDetails(movieID: Int): LiveData<Result<MovieDetailsResponse>> {
        val movieDetailsResponse = resultsLiveData(
            networkCall = { remoteSource.fetchMovieDetails(movieID) },
            saveCallResult = { dao.insertMovieAllDetails(it) },
            databaseQuery = { dao.getMovieAllDetails() }
        )
        return movieDetailsResponse
    }

    suspend fun getCastCrewDetails(movieID: Int): LiveData<Result<CastCrewListResponse>> {
        val castCrewResponse = resultsLiveData(
            networkCall = { remoteSource.fetchMovieCastCrewDetails(movieID) },
            saveCallResult = { dao.insertCastCrewDetails(it) },
            databaseQuery = { dao.getCastCrewDetails() }
        )
        return castCrewResponse
    }

    suspend fun getActorDetails(personID: Int): LiveData<Result<ActorDetailsResponse>> {
        val actorDetailsResponse = resultsLiveData(
            networkCall = { remoteSource.fetchActorDetails(personID) },
            saveCallResult = { dao.insertActorDetails(it) },
            databaseQuery = { dao.getActorDetails() }
        )
        return actorDetailsResponse
    }

    suspend fun getActorMovies(personID: Int): LiveData<Result<List<ActorMovies>>> {
        val actorMoviesResponse = resultsLiveData(
            networkCall = { remoteSource.fetchActorMovies(personID) },
            saveCallResult = { dao.insertActorMoviesList(it.cast) },
            databaseQuery = { dao.getActorMoviesList() }
        )
        return actorMoviesResponse
    }

    suspend fun getMovieVideos(movieID: Int): LiveData<Result<List<MovieVideos>>> {
        val moviesVideosResponse = resultsLiveData(
            networkCall = { remoteSource.fetchMovieVideos(movieID) },
            saveCallResult = { dao.insertMovieVideosList(it.results) },
            databaseQuery = { dao.getMoviesVideosList() }
        )
        return moviesVideosResponse
    }

    suspend fun getMovieReviews(movieID: Int): LiveData<Result<List<ReviewList>>> {
        val moviesReviewsResponse = resultsLiveData(
            networkCall = { remoteSource.fetchMovieReviews(movieID) },
            saveCallResult = { dao.insertMoviesReviews(it.results) },
            databaseQuery = { dao.getMoviesReviewsList() }
        )
        return moviesReviewsResponse
    }

    suspend fun getSimilarMovies(movieID: Int): LiveData<Result<List<SimilarMovie>>> {
        val similarMoviesResponse = resultsLiveData(
            networkCall = { remoteSource.fetchSimilarMovies(movieID) },
            saveCallResult = { dao.insertSimilarMoviesList(it.results) },
            databaseQuery = { dao.getSimilarMoviesList() }
        )
        return similarMoviesResponse
    }

    suspend fun getActorImages(personID: Int): LiveData<Result<List<BackdropImages>>> {
        val personImages = resultsLiveData(
            networkCall = { remoteSource.fetchActorImages(personID) },
            saveCallResult = { dao.insertActorImages(it.profiles) },
            databaseQuery = { dao.getActorImagesList() }
        )
        return personImages
    }

    suspend fun getMovieImages(movieID: Int): LiveData<Result<List<BackdropImages>>> {
        val movieImages = resultsLiveData(
            networkCall = { remoteSource.fetchMovieImages(movieID) },
            saveCallResult = { dao.insertMovieImages(it.backdrops) },
            databaseQuery = { dao.getBackDropImages() }
        )
        return movieImages
    }

    suspend fun getExternalID(personID: Int): LiveData<Result<ExternalIDs>> {
        val externalID = resultsLiveData(
            networkCall = { remoteSource.fetchExternalID(personID) },
            saveCallResult = { dao.insertExternalIDs(it) },
            databaseQuery = { dao.getExternalIDs() }
        )
        return externalID
    }

    suspend fun getMovieGenreList(): LiveData<Result<List<MovieGenreList>>> {
        val movieGenre = resultsLiveData(
            networkCall = { remoteSource.fetchMovieGenreList() },
            saveCallResult = { dao.insertMovieGenres(it.genres) },
            databaseQuery = { dao.getMovieGenres() }
        )
        return movieGenre
    }

    suspend fun getHollywoodMoviesDiscover(
        language: String,
        genres: Int
    ): LiveData<Result<List<HollyWoodMoviesList>>> {
        val hollywoodMoviesResponse = resultsLiveData(
            networkCall = { remoteSource.fetchHollywoodMoviesDiscover(language, genres) },
            saveCallResult = { dao.insertHollyWoodMoviesList(it.results) },
            databaseQuery = { dao.getHollyWoodMoviesList() }
        )
        return hollywoodMoviesResponse
    }

    suspend fun getKollywoodMoviesDiscover(
        language: String,
        genres: Int
    ): LiveData<Result<List<KollyWoodMoviesList>>> {
        val kollywoodMoviesResponse = resultsLiveData(
            networkCall = { remoteSource.fetchKollywoodMoviesDiscover(language, genres) },
            saveCallResult = { dao.insertKollyWoodMoviesList(it.results) },
            databaseQuery = { dao.getKollyWoodMoviesList() }
        )
        return kollywoodMoviesResponse
    }

    suspend fun getBollywoodMoviesDiscover(
        language: String,
        genres: Int
    ): LiveData<Result<List<BollyWoodMoviesList>>> {
        val bollywoodMoviesResponse = resultsLiveData(
            networkCall = { remoteSource.fetchBollywoodMoviesDiscover(language, genres) },
            saveCallResult = { dao.insertBollyWoodMoviesList(it.results) },
            databaseQuery = { dao.getBollyWoodMoviesList() }
        )
        return bollywoodMoviesResponse
    }

    suspend fun getMollywoodMoviesDiscover(
        language: String,
        genres: Int
    ): LiveData<Result<List<MollyWoodMoviesList>>> {
        val mollywoodMoviesResponse = resultsLiveData(
            networkCall = { remoteSource.fetchMollywoodMoviesDiscover(language, genres) },
            saveCallResult = { dao.insertMollyWoodMoviesList(it.results) },
            databaseQuery = { dao.getMollyWoodMoviesList() }
        )
        return mollywoodMoviesResponse
    }

    suspend fun getKoreanMoviesDiscover(
        language: String,
        genres: Int
    ): LiveData<Result<List<KoreanMoviesList>>> {
        val koreanMoviesResponse = resultsLiveData(
            networkCall = { remoteSource.fetchKoreanMoviesDiscover(language, genres) },
            saveCallResult = { dao.insertKoreanMoviesList(it.results) },
            databaseQuery = { dao.getKoreanMoviesList() }
        )
        return koreanMoviesResponse
    }


    fun delete() {
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteMoviesAllDetails()
        }
    }

    fun actorDetailsDelete() {
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteAllActorDetails()
        }
    }

    fun actorMoviesDelete() {
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteAllActorMoviesDetails()
        }
    }

    fun castDelete() {
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteAllCastDetails()
        }
    }

    fun videosDelete() {
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteAllVideosDetails()
        }
    }

    fun similarMoviesDelete() {
        CoroutineScope(Dispatchers.IO).launch {
            dao.similarMoviesDelete()
        }
    }

    fun actorImagesDelete() {
        CoroutineScope(Dispatchers.IO).launch {
            dao.actorImagesDelete()
        }
    }

    fun moviesImagesDelete() {
        CoroutineScope(Dispatchers.IO).launch {
            dao.moviesImagesDelete()
        }
    }

    fun externalIDDelete() {
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteExternalIDs()
        }
    }

    fun moviesReviewsDelete() {
        CoroutineScope(Dispatchers.IO).launch {
            dao.moviesReviewsDelete()
        }
    }

    fun deleteHollyMovies() {
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteHollyMovies()
        }
    }

    fun deleteKollyWoodMovies() {
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteKollyWoodMovies()
        }
    }

    fun deleteBollyWoodMovies() {
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteBollyWoodMovies()
        }
    }

    fun deleteMollyWoodMovies() {
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteMollyWoodMovies()
        }
    }

    fun deleteKoreanMovies() {
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteKoreanMoviesList()
        }
    }

}