package com.example.moviesdataapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "productionCompanies")
data class ProductionCompanies(
    @PrimaryKey(autoGenerate = true)
    @field:SerializedName("company_id")
    val company_id: Int,
    @field:SerializedName("logo_path")
    val logo_path: String,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("origin_country")
    val origin_country: String
)