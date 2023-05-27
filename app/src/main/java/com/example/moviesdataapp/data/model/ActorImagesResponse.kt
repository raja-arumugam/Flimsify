package com.example.moviesdataapp.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ActorImagesResponse(
    val id: Int,
    val profiles: List<BackdropImages>
) : Parcelable

/*
@Parcelize
@Entity(tableName = "actorImages")
data class BackdropImages(
    @field:SerializedName("aspect_ratio")
    val aspect_ratio: Double?,
    @field:SerializedName("file_path")
    val file_path: String?,
    @field:SerializedName("height")
    val height: Int?,
    @field:SerializedName("vote_average")
    val vote_average: Double?,
    @field:SerializedName("vote_count")
    val vote_count: Int?,
    @field:SerializedName("width")
    val width: Int?
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}*/
