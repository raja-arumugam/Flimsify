package com.example.moviesdataapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class SeasonImages(
    val id: Int,
    val posters: List<BackdropImages>
)

/*
@Entity(tableName = "seasonPosters")
data class SeasonPoster(
    @field:SerializedName("aspect_ratio")
    val aspect_ratio: Double,
    @field:SerializedName("file_path")
    val file_path: String?,
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}*/
