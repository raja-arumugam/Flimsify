package com.example.moviesdataapp.data.api

import com.example.moviesdataapp.data.model.*
import com.example.moviesdataapp.data.model.CastCrewSeasonResponse
import com.example.moviesdataapp.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {

    @GET(Constants.Endpoints.GET_NOW_PLAYING_MOVIES)
    suspend fun getNowPlayingMovie(
        @Query("api_key")
        key: String
    ): Response<NowPlayingMovie>

    @GET(Constants.Endpoints.GET_POPULAR_MOVIES)
    suspend fun getPopularMovies(
        @Query("api_key")
        key: String
    ): Response<PopularMoviesResponse>

    @GET(Constants.Endpoints.GET_UPCOMING_MOVIES)
    suspend fun getUpcomingMovie(
        @Query("api_key")
        key: String
    ): Response<UpcomingMovieResponse>

    @GET(Constants.Endpoints.GET_DISCOVER_MOVIES)
    suspend fun getActionMovies(
        @Query("api_key")
        key: String,
        @Query("with_genres")
        genresID: Int
    ): Response<ActionMoviesResponse>

    @GET(Constants.Endpoints.GET_TOP_RATED_TV)
    suspend fun getTopRatedTv(
        @Query("api_key")
        key: String
    ): Response<TopRatedTvResponse>

    @GET(Constants.Endpoints.GET_POPULAR_TV)
    suspend fun getPopularTv(
        @Query("api_key")
        key: String
    ): Response<PopularTvReponse>

    @GET(Constants.Endpoints.GET_AIRING_TODAY_TV)
    suspend fun getAiringTodayTV(
        @Query("api_key")
        key: String
    ): Response<AiringTodayTvResponse>

    @GET(Constants.Endpoints.GET_MOVIE_DETAILS)
    suspend fun getMovieAllDetails(
        @Path("movie_id")
        movieID: Int,
        @Query("api_key")
        apiKey: String
    ): Response<MovieDetailsResponse>

    @GET(Constants.Endpoints.GET_MOVIE_CREDITS)
    suspend fun getMovieCastCrewDetails(
        @Path("movie_id")
        movieID: Int,
        @Query("api_key")
        apiKey: String
    ): Response<CastCrewListResponse>

    @GET(Constants.Endpoints.GET_PERSON_DETAIL)
    suspend fun getActorDetails(
        @Path("person_id")
        movieID: Int,
        @Query("api_key")
        apiKey: String
    ): Response<ActorDetailsResponse>

    @GET(Constants.Endpoints.GET_PERSON_MOVIE_CREDITS)
    suspend fun getActorMovies(
        @Path("person_id")
        movieID: Int,
        @Query("api_key")
        apiKey: String
    ): Response<ActorsMovieCredits>

    @GET(Constants.Endpoints.GET_MOVIE_VIDEOS)
    suspend fun getMovieVideos(
        @Path("movie_id")
        movieID: Int,
        @Query("api_key")
        apiKey: String
    ): Response<MovieVideosResponse>

    @GET(Constants.Endpoints.GET_MOVIE_REVIEWS)
    suspend fun getMovieReviews(
        @Path("movie_id")
        movieID: Int,
        @Query("api_key")
        apiKey: String
    ): Response<ReviewsListResponse>

    @GET(Constants.Endpoints.GET_MOVIE_SIMILAR)
    suspend fun getSimilarMovies(
        @Path("movie_id")
        movieID: Int,
        @Query("api_key")
        apiKey: String
    ): Response<SimilarMoviesListResponse>

    @GET(Constants.Endpoints.GET_PERSON_IMAGES)
    suspend fun getActorImages(
        @Path("person_id")
        personID: Int,
        @Query("api_key")
        apiKey: String
    ): Response<ActorImagesResponse>

    @GET(Constants.Endpoints.GET_TV_DETAILS)
    suspend fun getTvDetails(
        @Path("tv_id")
        TvId: Int,
        @Query("api_key")
        apiKey: String
    ): Response<TvDetailsResponse>

    @GET(Constants.Endpoints.GET_TV_CREDITS)
    suspend fun getCastCrewDetails(
        @Path("tv_id")
        TvId: Int,
        @Query("api_key")
        apiKey: String
    ): Response<CastCrewListResponse>

    @GET(Constants.Endpoints.GET_TV_VIDEOS)
    suspend fun getVideosDetails(
        @Path("tv_id")
        TvId: Int,
        @Query("api_key")
        apiKey: String
    ): Response<MovieVideosResponse>

    @GET(Constants.Endpoints.GET_TV_SIMILAR)
    suspend fun getSimilarTvShows(
        @Path("tv_id")
        TvId: Int,
        @Query("api_key")
        apiKey: String
    ): Response<SimilarTvShows>

    @GET(Constants.Endpoints.GET_TV_SEASONS_DETAILS)
    suspend fun getSeasonDetails(
        @Path("tv_id")
        TvId: Int,
        @Path("season_number")
        seasonNumber: Int,
        @Query("api_key")
        apiKey: String
    ): Response<SeasonDetails>

    @GET(Constants.Endpoints.GET_TV_SEASONS_CREDITS)
    suspend fun getSeasonCastCrewDetails(
        @Path("tv_id")
        TvId: Int,
        @Path("season_number")
        seasonNumber: Int,
        @Query("api_key")
        apiKey: String
    ): Response<CastCrewSeasonResponse>

    @GET(Constants.Endpoints.GET_TV_SEASONS_IMAGES)
    suspend fun getSeasonImages(
        @Path("tv_id")
        TvId: Int,
        @Path("season_number")
        seasonNumber: Int,
        @Query("api_key")
        apiKey: String
    ): Response<SeasonImages>

    @GET(Constants.Endpoints.GET_TV_IMAGES)
    suspend fun getTvImages(
        @Path("tv_id")
        TvId: Int,
        @Query("api_key")
        apiKey: String
    ): Response<TvImages>

    @GET(Constants.Endpoints.GET_MOVIE_IMAGES)
    suspend fun getMovieImages(
        @Path("movie_id")
        TvId: Int,
        @Query("api_key")
        apiKey: String
    ): Response<TvImages>

    @GET(Constants.Endpoints.GET_EXTERNAL_IDS)
    suspend fun getExternalIds(
        @Path("person_id")
        personId: Int,
        @Query("api_key")
        apiKey: String
    ): Response<ExternalIDs>

    @GET(Constants.Endpoints.GET_MOVIE_GENRES)
    suspend fun getMovieGenreList(
        @Query("api_key")
        apiKey: String
    ): Response<MovieGenreListResponse>

    @GET(Constants.Endpoints.GET_DISCOVER_MOVIES)
    suspend fun getHollywoodMoviesDiscover(
        @Query("api_key")
        key: String,
        @Query("with_original_language")
        language: String,
        @Query("with_genres")
        genresID: Int
    ): Response<HollyWoodMoviesListResponse>

    @GET(Constants.Endpoints.GET_DISCOVER_MOVIES)
    suspend fun getKollywoodMoviesDiscover(
        @Query("api_key")
        key: String,
        @Query("with_original_language")
        language: String,
        @Query("with_genres")
        genresID: Int
    ): Response<KollyWoodMoviesListResponse>

    @GET(Constants.Endpoints.GET_DISCOVER_MOVIES)
    suspend fun getBollywoodMoviesDiscover(
        @Query("api_key")
        key: String,
        @Query("with_original_language")
        language: String,
        @Query("with_genres")
        genresID: Int
    ): Response<BollyWoodMoviesListResponse>

    @GET(Constants.Endpoints.GET_DISCOVER_MOVIES)
    suspend fun getMollywoodMoviesDiscover(
        @Query("api_key")
        key: String,
        @Query("with_original_language")
        language: String,
        @Query("with_genres")
        genresID: Int
    ): Response<MollyWoodMoviesListResponse>

    @GET(Constants.Endpoints.GET_DISCOVER_MOVIES)
    suspend fun getKoreanMoviesDiscover(
        @Query("api_key")
        key: String,
        @Query("with_original_language")
        language: String,
        @Query("with_genres")
        genresID: Int
    ): Response<KoreanMoviesListResponse>

}