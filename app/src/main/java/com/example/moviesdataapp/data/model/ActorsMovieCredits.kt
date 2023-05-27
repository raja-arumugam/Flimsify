package com.example.moviesdataapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class ActorsMovieCredits(
    val cast: List<ActorMovies>,
    val id: Int
)

@Entity(tableName = "actorMovies")
data class ActorMovies(
    @field:SerializedName("adult")
    val adult: Boolean?,
    @field:SerializedName("backdrop_path")
    val backdrop_path: String?,
    @field:SerializedName("character")
    val character: String?,
    @field:SerializedName("credit_id")
    val credit_id: String?,
    @PrimaryKey(autoGenerate = true)
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("original_title")
    val original_title: String?,
    @field:SerializedName("popularity")
    val popularity: Double?,
    @field:SerializedName("poster_path")
    val poster_path: String?,
    @field:SerializedName("title")
    val title: String?
)
