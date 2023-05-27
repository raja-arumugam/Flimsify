package com.example.moviesdataapp.data.repository

import com.example.moviesdataapp.BuildConfig
import com.example.moviesdataapp.data.api.APIService
import com.example.moviesdataapp.data.api.BaseApiDataSource
import javax.inject.Inject

class TvApiRemoteSource @Inject constructor(private val service: APIService) : BaseApiDataSource() {

    suspend fun fetchTvTopRate() = getResult {
        service.getTopRatedTv(BuildConfig.API_KEY)
    }

    suspend fun fetchPopular() = getResult {
        service.getPopularTv(BuildConfig.API_KEY)
    }

    suspend fun fetchAiringToday() = getResult {
        service.getAiringTodayTV(BuildConfig.API_KEY)
    }

    suspend fun fetchTVDetails(tvId: Int) = getResult {
        service.getTvDetails(tvId, BuildConfig.API_KEY)
    }

    suspend fun fetchCastCrewDetails(tvId: Int) = getResult {
        service.getCastCrewDetails(tvId, BuildConfig.API_KEY)
    }

    suspend fun fetchVideosDetails(tvId: Int) = getResult {
        service.getVideosDetails(tvId, BuildConfig.API_KEY)
    }

    suspend fun fetchSimilarTv(tvId: Int) = getResult {
        service.getSimilarTvShows(tvId, BuildConfig.API_KEY)
    }

    suspend fun fetchSeasonDetails(tvId: Int, seasonNumber: Int) = getResult {
        service.getSeasonDetails(tvId, seasonNumber, BuildConfig.API_KEY)
    }

    suspend fun fetchSeasonCastCrewDetails(tvId: Int, seasonNumber: Int) = getResult {
        service.getSeasonCastCrewDetails(tvId, seasonNumber, BuildConfig.API_KEY)
    }

    suspend fun fetchSeasonImages(tvId: Int, seasonNumber: Int) = getResult {
        service.getSeasonImages(tvId, seasonNumber, BuildConfig.API_KEY)
    }

    suspend fun fetchTvImages(tvId: Int) = getResult {
        service.getTvImages(tvId, BuildConfig.API_KEY)
    }



}