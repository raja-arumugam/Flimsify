package com.example.moviesdataapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class TopRatedTvResponse(
    val page: Int,
    val results: List<TopRatedTV>,
    val total_pages: Int,
    val total_results: Int
)

@Entity(tableName = "topRatedTv")
data class TopRatedTV(
    @field:SerializedName("backdrop_path")
    val backdrop_path: String,
    @field:SerializedName("first_air_date")
    val first_air_date: String,
    @PrimaryKey(autoGenerate = true)
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("original_language")
    val original_language: String,
    @field:SerializedName("original_name")
    val original_name: String,
    @field:SerializedName("overview")
    val overview: String,
    @field:SerializedName("popularity")
    val popularity: Double,
    @field:SerializedName("poster_path")
    val poster_path: String,
    @field:SerializedName("vote_average")
    val vote_average: Double,
    @field:SerializedName("vote_count")
    val vote_count: Int
)