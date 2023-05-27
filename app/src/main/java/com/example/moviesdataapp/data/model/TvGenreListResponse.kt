package com.example.moviesdataapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class TvGenreListResponse(
    val genres: List<TvGenreList>
)

@Entity(tableName = "movieGenres")
data class TvGenreList(
    @PrimaryKey(autoGenerate = true)
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("name")
    val name: String
)