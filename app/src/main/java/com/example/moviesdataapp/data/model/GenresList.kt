package com.example.moviesdataapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "genresList")
data class GenresList(
    @PrimaryKey(autoGenerate = true)
    @field:SerializedName("genres_id")
    val genres_id: Int,
    @field:SerializedName("name")
    val name: String
)