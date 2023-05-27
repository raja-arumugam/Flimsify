package com.example.moviesdataapp.data.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "moviesAllDetails")
data class MovieDetailsResponse(
    @field:SerializedName("adult")
    val adult: Boolean,
    @field:SerializedName("backdrop_path")
    val backdrop_path: String?,
    @field:SerializedName("budget")
    val budget: Int,
    @SerializedName("genres")
    @Expose
    @NonNull
    val genres: List<GenresList>,
    @field:SerializedName("homepage")
    val homepage: String,
    @PrimaryKey(autoGenerate = true)
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("imdb_id")
    val imdb_id: String?,
    @field:SerializedName("original_language")
    val original_language: String,
    @field:SerializedName("original_title")
    val original_title: String,
    @field:SerializedName("overview")
    val overview: String,
    @field:SerializedName("popularity")
    val popularity: Double,
    @field:SerializedName("poster_path")
    val poster_path: String?,
    @SerializedName("production_companies")
    @Expose
    @NonNull
    val production_companies: List<ProductionCompanies>,
    @field:SerializedName("release_date")
    val release_date: String,
    @field:SerializedName("revenue")
    val revenue: Int?,
    @field:SerializedName("runtime")
    val runtime: Int,
    @field:SerializedName("status")
    val status: String?,
    @field:SerializedName("tagline")
    val tagline: String?,
    @field:SerializedName("title")
    val title: String,
    @field:SerializedName("video")
    val video: Boolean,
    @field:SerializedName("vote_average")
    val vote_average: Double?,
    @field:SerializedName("vote_count")
    val vote_count: Int?
)