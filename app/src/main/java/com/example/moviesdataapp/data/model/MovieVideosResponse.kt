package com.example.moviesdataapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class MovieVideosResponse(
    val id: Int,
    val results: List<MovieVideos>
)

@Entity(tableName = "movieVideos")
data class MovieVideos(
    @field:SerializedName("id")
    val id: String,
    @field:SerializedName("iso_3166_1")
    val iso_3166_1: String,
    @field:SerializedName("iso_639_1")
    val iso_639_1: String,
    @field:SerializedName("key")
    val key: String,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("official")
    val official: Boolean,
    @field:SerializedName("published_at")
    val published_at: String,
    @field:SerializedName("site")
    val site: String,
    @field:SerializedName("size")
    val size: Int,
    @field:SerializedName("type")
    val type: String
) {
    @PrimaryKey(autoGenerate = true)
    var rowID = 0
}