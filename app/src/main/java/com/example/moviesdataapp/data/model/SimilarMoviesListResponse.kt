package com.example.moviesdataapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class SimilarMoviesListResponse(
    val page: Int,
    val results: List<SimilarMovie>
)

@Entity(tableName = "similarMovies")
data class SimilarMovie(
    @field:SerializedName("adult")
    val adult: Boolean,
    @field:SerializedName("backdrop_path")
    val backdrop_path: String?,
    @PrimaryKey(autoGenerate = true)
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("original_language")
    val original_language: String,
    @field:SerializedName("original_title")
    val original_title: String,
    @field:SerializedName("overview")
    val overview: String,
    @field:SerializedName("poster_path")
    val poster_path: String?,
    @field:SerializedName("title")
    val title: String,
)