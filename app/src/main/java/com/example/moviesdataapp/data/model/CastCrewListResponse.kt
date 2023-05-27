package com.example.moviesdataapp.data.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "castCrew")
data class CastCrewListResponse(
    @SerializedName("cast")
    @Expose
    @NonNull
    val cast: List<Cast>,
    @SerializedName("crew")
    @Expose
    @NonNull
    val crew: List<Crew>,
    @PrimaryKey(autoGenerate = true)
    @field:SerializedName("id")
    val id: Int
)

@Entity(tableName = "castDetails")
data class Cast(
    @field:SerializedName("adult")
    val adult: Boolean,
    @field:SerializedName("cast_id")
    val cast_id: Int,
    @field:SerializedName("character")
    val character: String,
    @field:SerializedName("credit_id")
    val credit_id: String,
    @field:SerializedName("gender")
    val gender: Int,
    @PrimaryKey(autoGenerate = true)
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("known_for_department")
    val known_for_department: String,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("order")
    val order: Int,
    @field:SerializedName("original_name")
    val original_name: String,
    @field:SerializedName("popularity")
    val popularity: Double,
    @field:SerializedName("profile_path")
    val profile_path: String?
)

@Entity(tableName = "crewDetails")
data class Crew(
    @field:SerializedName("adult")
    val adult: Boolean,
    @field:SerializedName("credit_id")
    val credit_id: String,
    @field:SerializedName("department")
    val department: String,
    @field:SerializedName("gender")
    val gender: Int,
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
    @field:SerializedName("popularity")
    val popularity: Double,
    @field:SerializedName("profile_path")
    val profile_path: String?
)