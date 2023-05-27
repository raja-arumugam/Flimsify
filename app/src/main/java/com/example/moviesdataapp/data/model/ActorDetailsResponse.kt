package com.example.moviesdataapp.data.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "actorDetails")
data class ActorDetailsResponse(
    @field:SerializedName("adult")
    val adult: Boolean?,
    @SerializedName("also_known_as")
    @Expose
    @NonNull
    val also_known_as: List<String>,
    @field:SerializedName("biography")
    val biography: String?,
    @field:SerializedName("birthday")
    val birthday: String?,
    @field:SerializedName("gender")
    val gender: Int?,
    @PrimaryKey(autoGenerate = true)
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("known_for_department")
    val known_for_department: String?,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("place_of_birth")
    val place_of_birth: String?,
    @field:SerializedName("popularity")
    val popularity: Double?,
    @field:SerializedName("profile_path")
    val profile_path: String
)

@Entity(tableName = "alsoKnownAs")
data class AlsoKnownAs(
    @PrimaryKey(autoGenerate = true)
    @field:SerializedName("id")
    val id: Int,
)