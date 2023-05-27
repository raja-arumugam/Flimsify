package com.example.moviesdataapp.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TvImages(
    val backdrops: ArrayList<BackdropImages>,
    val id: Int,
) : Parcelable

@Parcelize
@Entity(tableName = "backdropImage")
data class BackdropImages(
    @field:SerializedName("aspect_ratio")
    val aspect_ratio: Double,
    @field:SerializedName("file_path")
    val file_path: String?,
    @field:SerializedName("height")
    val height: Int,
    @field:SerializedName("width")
    val width: Int
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}