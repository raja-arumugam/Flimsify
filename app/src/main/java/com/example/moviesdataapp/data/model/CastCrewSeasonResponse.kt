package com.example.moviesdataapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class CastCrewSeasonResponse(
    val cast: List<SeriesCast>,
    val crew: List<SeriesCrew>,
    val id: Int
)

@Entity(tableName = "seriesCasts")
data class SeriesCast(
    @field:SerializedName("character")
    val character: String?,
    @field:SerializedName("gender")
    val gender: Int,
    @PrimaryKey(autoGenerate = true)
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("known_for_department")
    val known_for_department: String,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("original_name")
    val original_name: String,
    @field:SerializedName("profile_path")
    val profile_path: String?
)

@Entity(tableName = "seriesCrews")
data class SeriesCrew(
    @field:SerializedName("department")
    val department: String,
    @field:SerializedName("gender")
    val gender: Int?,
    @PrimaryKey(autoGenerate = true)
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("job")
    val job: String,
    @field:SerializedName("known_for_department")
    val known_for_department: String,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("original_name")
    val original_name: String,
    @field:SerializedName("profile_path")
    val profile_path: String?
)