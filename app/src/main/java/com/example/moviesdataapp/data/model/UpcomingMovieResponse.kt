package com.example.moviesdataapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class UpcomingMovieResponse(
    val page: Int,
    val results: List<UpcomingMovie>,
    val total_pages: Int,
    val total_results: Int
)

@Entity(tableName = "upComingMovies")
data class UpcomingMovie(
    @field:SerializedName("adult")
    val adult: Boolean,
    @field:SerializedName("backdrop_path")
    val backdrop_path: String?,
    @PrimaryKey(autoGenerate = true)
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("original_language")
    val original_language: String?,
    @field:SerializedName("original_title")
    val original_title: String,
    @field:SerializedName("overview")
    val overview: String?,
    @field:SerializedName("popularity")
    val popularity: Double?,
    @field:SerializedName("poster_path")
    val poster_path: String?,
    @field:SerializedName("release_date")
    val release_date: String,
    @field:SerializedName("title")
    val title: String,
    @field:SerializedName("vote_average")
    val vote_average: Double,
    @field:SerializedName("vote_count")
    val vote_count: Int?
)