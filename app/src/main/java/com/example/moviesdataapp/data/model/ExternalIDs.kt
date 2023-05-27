package com.example.moviesdataapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "externalIDs")
data class ExternalIDs(
    @field:SerializedName("facebook_id")
    val facebook_id: String?,
    @PrimaryKey(autoGenerate = true)
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("instagram_id")
    val instagram_id: String?,
    @field:SerializedName("twitter_id")
    val twitter_id: String?,
    @field:SerializedName("youtube_id")
    val youtube_id: String?
)