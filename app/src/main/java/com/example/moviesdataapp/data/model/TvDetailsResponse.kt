package com.example.moviesdataapp.data.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity("tvAllDetails")
data class TvDetailsResponse(
    @field:SerializedName("adult")
    val adult: Boolean,
    @field:SerializedName("backdrop_path")
    val backdrop_path: String?,
    @SerializedName("created_by")
    @Expose
    @NonNull
    val created_by: List<CreatedBy>,
    @field:SerializedName("first_air_date")
    val first_air_date: String,
    @SerializedName("genres")
    @Expose
    @NonNull
    val genres: List<GenresList>,
    @field:SerializedName("homepage")
    val homepage: String?,
    @PrimaryKey(autoGenerate = true)
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("in_production")
    val in_production: Boolean,
    @field:SerializedName("name")
    val name: String,
    @SerializedName("networks")
    @Expose
    @NonNull
    val networks: List<Network>,
    @field:SerializedName("number_of_episodes")
    val number_of_episodes: Int,
    @field:SerializedName("number_of_seasons")
    val number_of_seasons: Int,
    @field:SerializedName("original_language")
    val original_language: String,
    @field:SerializedName("original_name")
    val original_name: String,
    @field:SerializedName("overview")
    val overview: String?,
    @field:SerializedName("popularity")
    val popularity: Double,
    @field:SerializedName("poster_path")
    val poster_path: String?,
    @SerializedName("production_companies")
    @Expose
    @NonNull
    val production_companies: List<ProductionCompanies>,
    @SerializedName("seasons")
    @Expose
    @NonNull
    val seasons: List<Season>,
    @SerializedName("episode_run_time")
    @Expose
    @NonNull
    val episode_run_time: List<String>,
    @field:SerializedName("status")
    val status: String,
    @field:SerializedName("tagline")
    val tagline: String,
    @field:SerializedName("type")
    val type: String,
    @field:SerializedName("vote_average")
    val vote_average: Double,
    @field:SerializedName("vote_count")
    val vote_count: Int
)

@Entity("createdBy")
data class CreatedBy(
    @field:SerializedName("credit_id")
    val credit_id: String,
    @field:SerializedName("gender")
    val gender: Int,
    @PrimaryKey(autoGenerate = true)
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("profile_path")
    val profile_path: String
)

@Entity(tableName = "network")
data class Network(
    @PrimaryKey(autoGenerate = true)
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("logo_path")
    val logo_path: String,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("origin_country")
    val origin_country: String
)

@Entity(tableName = "season")
data class Season(
    @field:SerializedName("air_date")
    val air_date: String,
    @field:SerializedName("episode_count")
    val episode_count: Int,
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

@Entity(tableName = "episodeRunTime")
data class EpisodeRunTime(
    @PrimaryKey(autoGenerate = true)
    @field:SerializedName("id")
    val id : Int,
)
