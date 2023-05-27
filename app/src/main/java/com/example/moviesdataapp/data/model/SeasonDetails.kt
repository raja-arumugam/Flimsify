package com.example.moviesdataapp.data.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "seasonDetails")
data class SeasonDetails(
    @field:SerializedName("air_date")
    val air_date: String,
    @SerializedName("episodes")
    @Expose
    @NonNull
    val episodes: List<Episode>,
    @PrimaryKey(autoGenerate = true)
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("overview")
    val overview: String,
    @field:SerializedName("poster_path")
    val poster_path: String?,
    @field:SerializedName("season_number")
    val season_number: Int
)

@Entity(tableName = "seasonEpisodes")
data class Episode(
    @field:SerializedName("air_date")
    val air_date: String,
    @field:SerializedName("episode_number")
    val episode_number: Int,
    @PrimaryKey(autoGenerate = true)
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("overview")
    val overview: String,
    @field:SerializedName("production_code")
    val production_code: String,
    @field:SerializedName("runtime")
    val runtime: Int,
    @field:SerializedName("season_number")
    val season_number: Int,
    @field:SerializedName("show_id")
    val show_id: Int,
    @field:SerializedName("still_path")
    val still_path: String?,
    @field:SerializedName("vote_average")
    val vote_average: Double,
    @field:SerializedName("vote_count")
    val vote_count: Int
)