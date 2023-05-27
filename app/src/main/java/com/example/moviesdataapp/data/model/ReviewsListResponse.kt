package com.example.moviesdataapp.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class ReviewsListResponse(
    val id: Int,
    val page: Int,
    val results: List<ReviewList>,
    val total_pages: Int,
    val total_results: Int
)

@Entity(tableName = "reviewDetails")
data class ReviewList(
    @field:SerializedName("author")
    val author: String?,
    @SerializedName("author_details")
    @Embedded(prefix = "profile_")
    val author_details: AuthorDetails?,
    @field:SerializedName("content")
    val content: String,
    @field:SerializedName("created_at")
    val created_at: String?,
    @field:SerializedName("id")
    val id: String,
    @field:SerializedName("url")
    val url: String?
){
    @PrimaryKey(autoGenerate = true)
    var rowId = 0
}

data class AuthorDetails(
    val avatar_path: String?,
    val name: String,
    val rating: Double,
    val username: String
)