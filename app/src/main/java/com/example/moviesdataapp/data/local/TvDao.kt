package com.example.moviesdataapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviesdataapp.data.model.*
import com.example.moviesdataapp.data.model.SeriesCast
import com.example.moviesdataapp.data.model.SeriesCrew

@Dao
interface TvDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTopRatedTV(topRated: List<TopRatedTV>)

    @Query("SELECT * FROM topRatedTv ORDER BY id DESC")
    fun getTopRated(): LiveData<List<TopRatedTV>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPopularTV(popularTv: List<PopularTv>)

    @Query("SELECT * FROM popularTv ORDER BY id DESC")
    fun getPopular(): LiveData<List<PopularTv>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAiringTodayTV(airingTodayTv: List<AiringTodayTv>)

    @Query("SELECT * FROM airingTodayTv ORDER BY id DESC")
    fun getAiringToday(): LiveData<List<AiringTodayTv>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTvDetails(tvDetails: TvDetailsResponse)

    @Query("SELECT * FROM tvAllDetails ORDER BY id DESC")
    fun getTvDetails(): LiveData<TvDetailsResponse>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCastCrewDetails(castCrew: CastCrewListResponse)

    @Query("SELECT * FROM castCrew ORDER BY id DESC")
    fun getCastCrewDetails(): LiveData<CastCrewListResponse>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideosDetails(videosResponse: List<MovieVideos>)

    @Query("SELECT * FROM movieVideos ORDER BY id DESC")
    fun getVideosDetails(): LiveData<List<MovieVideos>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSimilarTv(similarTvShows: List<SimilarTv>)

    @Query("SELECT * FROM similarTv ORDER BY id DESC")
    fun getSimilarTv(): LiveData<List<SimilarTv>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeasonDetails(seasonDetails: SeasonDetails)

    @Query("SELECT * FROM seasonDetails ORDER BY id DESC")
    fun getSeasonDetails(): LiveData<SeasonDetails>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeasonCastDetails(castSeasonResponse: List<SeriesCast>)

    @Query("SELECT * FROM seriesCasts ORDER BY id DESC")
    fun getSeasonCastDetails(): LiveData<List<SeriesCast>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeasonCrewDetails(castSeasonResponse: List<SeriesCrew>)

    @Query("SELECT * FROM seriesCrews ORDER BY id DESC")
    fun getSeasonCrewDetails(): LiveData<List<SeriesCrew>>

   /* @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeasonImages(seasonImages: List<SeasonPoster>)

    @Query("SELECT * FROM seasonPosters ORDER BY id DESC")
    fun getSeasonImages(): LiveData<List<SeasonPoster>>*/

     @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeasonImages(seasonImages: List<BackdropImages>)

    @Query("SELECT * FROM backdropImage ORDER BY id DESC")
    fun getSeasonImages(): LiveData<List<BackdropImages>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTVImages(tvImages: List<BackdropImages>)

    @Query("SELECT * FROM backdropImage ORDER BY id DESC")
    fun getBackDropImages(): LiveData<List<BackdropImages>>


    @Query("DELETE FROM similarTv")
    fun deleteSimilarShows()

    @Query("DELETE FROM tvAllDetails")
    fun deleteTvDetails()

    @Query("DELETE FROM castCrew")
    fun deletecastDetails()

    @Query("DELETE FROM movieVideos")
    fun deleteVideos()

    @Query("DELETE FROM seasonDetails")
    fun deleteSeasonDetails()

    @Query("DELETE FROM seriesCasts")
    fun deleteSeasonCastList()

    @Query("DELETE FROM seriesCrews")
    fun deleteSeasonCrewList()

 /*   @Query("DELETE FROM seasonPosters")
    fun deleteSeasonImages()*/

    @Query("DELETE FROM backdropImage")
    fun deleteSeasonImages()

    @Query("DELETE FROM backdropImage")
    fun tvImagesDelete()

}