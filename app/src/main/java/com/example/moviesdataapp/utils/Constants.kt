package com.example.moviesdataapp.utils

object Constants {

    private const val BASE_URL_IMAGE = "https://image.tmdb.org/t/p/original"
    private const val BASE_URL_IMAGE_YOUTUBE = "https://img.youtube.com/vi/"
    private const val BASE_URL_IMAGE_USER_REVIEW = "https://image.tmdb.org/t/p/w200"

    fun getPosterPath(posterPath: String?): String {
        return BASE_URL_IMAGE + posterPath
    }

    fun getBackDropPath(backDropPath: String?): String {
        return BASE_URL_IMAGE + backDropPath
    }

    fun getUserPic(image: String?): String {
        return BASE_URL_IMAGE_USER_REVIEW + image
    }

    fun getYouTubePath(youTubePath: String?): String {
        return "$BASE_URL_IMAGE_YOUTUBE$youTubePath/0.jpg"
    }

    object Endpoints {
        const val GET_POPULAR_MOVIES = "movie/popular"
        const val GET_NOW_PLAYING_MOVIES = "movie/now_playing"
        const val GET_UPCOMING_MOVIES = "movie/upcoming"
        const val GET_DISCOVER_MOVIES = "discover/movie"
        const val GET_TOP_RATED_TV = "tv/top_rated"
        const val GET_POPULAR_TV = "tv/popular"
        const val GET_AIRING_TODAY_TV = "tv/airing_today"
        const val GET_MOVIE_GENRES = "genre/movie/list"
        const val GET_MOVIE_DETAILS = "movie/{movie_id}"
        const val GET_MOVIE_CREDITS = "movie/{movie_id}/credits"
        const val GET_MOVIE_REVIEWS = "movie/{movie_id}/reviews"
        const val GET_TV_DETAILS = "tv/{tv_id}"
        const val GET_TV_CREDITS = "tv/{tv_id}/credits"
        const val GET_MOVIE_VIDEOS = "movie/{movie_id}/videos"
        const val GET_TV_VIDEOS = "tv/{tv_id}/videos"
        const val GET_MOVIE_IMAGES = "movie/{movie_id}/images"
        const val GET_TV_IMAGES = "tv/{tv_id}/images"
        const val GET_MOVIE_SIMILAR = "movie/{movie_id}/similar"
        const val GET_TV_SIMILAR = "tv/{tv_id}/similar"
        const val GET_PERSON_DETAIL = "person/{person_id}"
        const val GET_PERSON_IMAGES = "person/{person_id}/images"
        const val GET_EXTERNAL_IDS = "person/{person_id}/external_ids"
        const val GET_PERSON_MOVIE_CREDITS = "person/{person_id}/movie_credits"
        const val GET_TV_SEASONS_DETAILS = "tv/{tv_id}/season/{season_number}"
        const val GET_TV_SEASONS_CREDITS = "tv/{tv_id}/season/{season_number}/credits"
        const val GET_TV_SEASONS_IMAGES = "tv/{tv_id}/season/{season_number}/images"

    }

}