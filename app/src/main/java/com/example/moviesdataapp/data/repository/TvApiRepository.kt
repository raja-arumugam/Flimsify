package com.example.moviesdataapp.data.repository

import androidx.lifecycle.LiveData
import com.example.moviesdataapp.data.api.Result
import com.example.moviesdataapp.data.api.resultLiveData
import com.example.moviesdataapp.data.api.resultsLiveData
import com.example.moviesdataapp.data.local.TvDao
import com.example.moviesdataapp.data.model.*
import com.example.moviesdataapp.data.model.SeriesCast
import com.example.moviesdataapp.data.model.SeriesCrew
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TvApiRepository @Inject constructor(
    private val dao: TvDao,
    private val remoteSource: TvApiRemoteSource
) {

    val topRatedTvData = resultLiveData(
        networkCall = { remoteSource.fetchTvTopRate() },
        saveCallResult = { dao.insertAllTopRatedTV(it.results) },
        databaseQuery = { dao.getTopRated() }
    )

    val popularTvData = resultLiveData(
        networkCall = { remoteSource.fetchPopular() },
        saveCallResult = { dao.insertAllPopularTV(it.results) },
        databaseQuery = { dao.getPopular() }
    )

    val airingTodayTvData = resultLiveData(
        networkCall = { remoteSource.fetchAiringToday() },
        saveCallResult = { dao.insertAllAiringTodayTV(it.results) },
        databaseQuery = { dao.getAiringToday() }
    )

    suspend fun getTvDetails(TvID: Int): LiveData<Result<TvDetailsResponse>> {
        val tvDetailsResponse = resultsLiveData(
            networkCall = { remoteSource.fetchTVDetails(TvID) },
            saveCallResult = { dao.insertAllTvDetails(it) },
            databaseQuery = { dao.getTvDetails() }
        )
        return tvDetailsResponse
    }

    suspend fun getCastCrewDetails(TvID: Int): LiveData<Result<CastCrewListResponse>> {
        val castCrewDetailsResponse = resultsLiveData(
            networkCall = { remoteSource.fetchCastCrewDetails(TvID) },
            saveCallResult = { dao.insertCastCrewDetails(it) },
            databaseQuery = { dao.getCastCrewDetails() }
        )
        return castCrewDetailsResponse
    }

    suspend fun getVideos(TvID: Int): LiveData<Result<List<MovieVideos>>> {
        val videosResponse = resultsLiveData(
            networkCall = { remoteSource.fetchVideosDetails(TvID) },
            saveCallResult = { dao.insertVideosDetails(it.results) },
            databaseQuery = { dao.getVideosDetails() }
        )
        return videosResponse
    }

    suspend fun getSimilarTv(TvID: Int): LiveData<Result<List<SimilarTv>>> {
        val similarTvResponse = resultsLiveData(
            networkCall = { remoteSource.fetchSimilarTv(TvID) },
            saveCallResult = { dao.insertSimilarTv(it.results) },
            databaseQuery = { dao.getSimilarTv() }
        )
        return similarTvResponse
    }

    suspend fun getSeasonDetails(TvID: Int, SeasonNumber: Int): LiveData<Result<SeasonDetails>> {
        val seasonResponse = resultsLiveData(
            networkCall = { remoteSource.fetchSeasonDetails(TvID, SeasonNumber) },
            saveCallResult = { dao.insertSeasonDetails(it) },
            databaseQuery = { dao.getSeasonDetails() }
        )
        return seasonResponse
    }

    suspend fun getSeasonCastDetails(
        TvID: Int,
        SeasonNumber: Int
    ): LiveData<Result<List<SeriesCast>>> {
        val seasonCastResponse = resultsLiveData(
            networkCall = { remoteSource.fetchSeasonCastCrewDetails(TvID, SeasonNumber) },
            saveCallResult = { dao.insertSeasonCastDetails(it.cast) },
            databaseQuery = { dao.getSeasonCastDetails() }
        )
        return seasonCastResponse
    }

    suspend fun getSeasonCrewDetails(
        TvID: Int,
        SeasonNumber: Int
    ): LiveData<Result<List<SeriesCrew>>> {
        val seasonCrewResponse = resultsLiveData(
            networkCall = { remoteSource.fetchSeasonCastCrewDetails(TvID, SeasonNumber) },
            saveCallResult = { dao.insertSeasonCrewDetails(it.crew) },
            databaseQuery = { dao.getSeasonCrewDetails() }
        )
        return seasonCrewResponse
    }

    /*suspend fun getSeasonImages(
        TvID: Int,
        SeasonNumber: Int
    ): LiveData<Result<List<SeasonPoster>>> {
        val seasonImageResponse = resultsLiveData(
            networkCall = { remoteSource.fetchSeasonImages(TvID, SeasonNumber) },
            saveCallResult = { dao.insertSeasonImages(it.posters) },
            databaseQuery = { dao.getSeasonImages() }
        )
        return seasonImageResponse
    }*/

    suspend fun getSeasonImages(
        TvID: Int,
        SeasonNumber: Int
    ): LiveData<Result<List<BackdropImages>>> {
        val seasonImageResponse = resultsLiveData(
            networkCall = { remoteSource.fetchSeasonImages(TvID, SeasonNumber) },
            saveCallResult = { dao.insertSeasonImages(it.posters) },
            databaseQuery = { dao.getSeasonImages() }
        )
        return seasonImageResponse
    }

    suspend fun getTvImages(TvID: Int): LiveData<Result<List<BackdropImages>>> {
        val tvImageResponse = resultsLiveData(
            networkCall = { remoteSource.fetchTvImages(TvID) },
            saveCallResult = { dao.insertTVImages(it.backdrops) },
            databaseQuery = { dao.getBackDropImages() }
        )
        return tvImageResponse
    }




    fun deleteTvDetails() {
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteTvDetails()
        }
    }

    fun castDelete() {
        CoroutineScope(Dispatchers.IO).launch {
            dao.deletecastDetails()
        }
    }

    fun videosDelete() {
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteVideos()
        }
    }

    fun similarShowsDelete() {
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteSimilarShows()
        }
    }

    fun deleteSeason() {
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteSeasonDetails()
        }
    }

    fun deleteSeasonCast() {
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteSeasonCastList()
        }
    }

    fun deleteSeasonCrew() {
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteSeasonCrewList()
        }
    }

    fun deleteSeasonImages() {
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteSeasonImages()
        }
    }

    fun tvImagesDelete() {
        CoroutineScope(Dispatchers.IO).launch {
            dao.tvImagesDelete()
        }
    }


}