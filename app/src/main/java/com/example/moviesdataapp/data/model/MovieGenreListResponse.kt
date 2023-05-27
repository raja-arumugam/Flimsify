package com.example.moviesdataapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class MovieGenreListResponse(
    val genres: List<MovieGenreList>
)

@Entity(tableName = "movieGenres")
data class MovieGenreList(
    @PrimaryKey(autoGenerate = true)
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("name")
    val name: String
)