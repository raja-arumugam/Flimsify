package com.example.moviesdataapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.moviesdataapp.data.model.*

@Dao
interface MovieDao {

    @Query("SELECT * FROM nowPlaying ORDER BY id DESC")
    fun getNowPlaying(): LiveData<List<NowPlayingMovies>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllNowPlaying(nowPlaying: List<NowPlayingMovies>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPopularMovies(popularMovies: List<PopularMovies>)

    @Query("SELECT * FROM popularMovies ORDER BY id DESC")
    fun getPopularMovies(): LiveData<List<PopularMovies>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllUpcomingMovies(upComing: List<UpcomingMovie>)

    @Query("SELECT * FROM upComingMovies ORDER BY id DESC")
    fun getUpcomingMovies(): LiveData<List<UpcomingMovie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllActionMovies(actionMovies: List<ActionMovies>)

    @Query("SELECT * FROM actionMovies ORDER BY id DESC")
    fun getActionMovies(): LiveData<List<ActionMovies>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieAllDetails(movieDetails: MovieDetailsResponse)

    @Query("SELECT * FROM moviesAllDetails ORDER BY id ASC")
    fun getMovieAllDetails(): LiveData<MovieDetailsResponse>

    @Query("SELECT * FROM moviesAllDetails ORDER BY id ASC")
    fun getNowPlayingMovieDetails(): LiveData<List<MovieDetailsResponse>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCastCrewDetails(castCrew: CastCrewListResponse)

    @Query("SELECT * FROM castCrew ORDER BY id ASC")
    fun getCastCrewDetails(): LiveData<CastCrewListResponse>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActorDetails(actorDetails: ActorDetailsResponse)

    @Query("SELECT * FROM actorDetails ORDER BY id ASC")
    fun getActorDetails(): LiveData<ActorDetailsResponse>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActorMoviesList(actorMovies: List<ActorMovies>)

    @Query("SELECT * FROM actorMovies ORDER BY id ASC")
    fun getActorMoviesList(): LiveData<List<ActorMovies>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieVideosList(moviesList: List<MovieVideos>)

    @Query("SELECT * FROM movieVideos ORDER BY id ASC")
    fun getMoviesVideosList(): LiveData<List<MovieVideos>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSimilarMoviesList(similarMovies: List<SimilarMovie>)

    @Query("SELECT * FROM similarMovies ORDER BY id ASC")
    fun getSimilarMoviesList(): LiveData<List<SimilarMovie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMoviesReviews(movieReviews: List<ReviewList>)

    @Query("SELECT * FROM reviewDetails ORDER BY id ASC")
    fun getMoviesReviewsList(): LiveData<List<ReviewList>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActorImages(actorImages: List<BackdropImages>)

    @Query("SELECT * FROM backdropImage ORDER BY id ASC")
    fun getActorImagesList(): LiveData<List<BackdropImages>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieImages(movieImages: List<BackdropImages>)

    @Query("SELECT * FROM backdropImage ORDER BY id DESC")
    fun getBackDropImages(): LiveData<List<BackdropImages>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExternalIDs(externalID: ExternalIDs)

    @Query("SELECT * FROM externalIDs ORDER BY id DESC")
    fun getExternalIDs(): LiveData<ExternalIDs>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieGenres(movieGenres: List<MovieGenreList>)

    @Query("SELECT * FROM movieGenres ORDER BY id DESC")
    fun getMovieGenres(): LiveData<List<MovieGenreList>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHollyWoodMoviesList(hollywoodMovies: List<HollyWoodMoviesList>)

    @Query("SELECT * FROM hollyWoodMoviesList ORDER BY id ASC")
    fun getHollyWoodMoviesList(): LiveData<List<HollyWoodMoviesList>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKollyWoodMoviesList(kollywoodMovies: List<KollyWoodMoviesList>)

    @Query("SELECT * FROM kollyWoodMoviesList ORDER BY id ASC")
    fun getKollyWoodMoviesList(): LiveData<List<KollyWoodMoviesList>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBollyWoodMoviesList(bollywoodMovies: List<BollyWoodMoviesList>)

    @Query("SELECT * FROM bollyWoodMoviesList ORDER BY id ASC")
    fun getBollyWoodMoviesList(): LiveData<List<BollyWoodMoviesList>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMollyWoodMoviesList(mollywoodMovies: List<MollyWoodMoviesList>)

    @Query("SELECT * FROM mollyWoodMoviesList ORDER BY id ASC")
    fun getMollyWoodMoviesList(): LiveData<List<MollyWoodMoviesList>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKoreanMoviesList(koreanMovies: List<KoreanMoviesList>)

    @Query("SELECT * FROM koreanMoviesList ORDER BY id ASC")
    fun getKoreanMoviesList(): LiveData<List<KoreanMoviesList>>




    @Query("DELETE FROM moviesAllDetails")
    fun deleteMoviesAllDetails()

    @Query("DELETE FROM actorDetails")
    fun deleteAllActorDetails()

    @Query("DELETE FROM actorMovies")
    fun deleteAllActorMoviesDetails()

    @Query("DELETE FROM castCrew")
    fun deleteAllCastDetails()

    @Query("DELETE FROM movieVideos")
    fun deleteAllVideosDetails()

    @Query("DELETE FROM similarMovies")
    fun similarMoviesDelete()

    @Query("DELETE FROM backdropImage")
    fun actorImagesDelete()

    @Query("DELETE FROM backdropImage")
    fun moviesImagesDelete()

    @Query("DELETE FROM externalIDs")
    fun deleteExternalIDs()

    @Query("DELETE FROM reviewDetails")
    fun moviesReviewsDelete()

    @Query("DELETE FROM movieGenres")
    fun deleteMovieGenre()

    @Query("DELETE FROM hollyWoodMoviesList")
    fun deleteHollyMovies()

    @Query("DELETE FROM kollyWoodMoviesList")
    fun deleteKollyWoodMovies()

    @Query("DELETE FROM bollyWoodMoviesList")
    fun deleteBollyWoodMovies()

    @Query("DELETE FROM mollyWoodMoviesList")
    fun deleteMollyWoodMovies()

    @Query("DELETE FROM koreanMoviesList")
    fun deleteKoreanMoviesList()


}