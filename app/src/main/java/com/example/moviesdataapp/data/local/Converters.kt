package com.example.moviesdataapp.data.local

import androidx.room.TypeConverter
import com.example.moviesdataapp.data.model.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*
import kotlin.jvm.internal.Ref.ObjectRef

/**
 * Type converters to allow Room to reference complex data types.
 */
class Converters {

    @TypeConverter
    fun fromProductionCompaniesList(productionCompanies: List<ProductionCompanies?>?): String? {
        val type = object : TypeToken<List<ProductionCompanies?>?>() {}.type
        return Gson().toJson(productionCompanies, type)
    }

    @TypeConverter
    fun toProductionCompaniesList(production_Companies: String?): List<ProductionCompanies>? {
        val type = object : TypeToken<List<ProductionCompanies?>?>() {}.type
        return Gson().fromJson<List<ProductionCompanies>>(production_Companies, type)
    }

    @TypeConverter
    fun fromMovieGenresList(movieGenres: List<GenresList?>?): String? {
        val type = object : TypeToken<List<GenresList?>?>() {}.type
        return Gson().toJson(movieGenres, type)
    }

    @TypeConverter
    fun toMovieGenresList(movie_Genres: String?): List<GenresList>? {
        val type = object : TypeToken<List<GenresList?>?>() {}.type
        return Gson().fromJson<List<GenresList>>(movie_Genres, type)
    }

    @TypeConverter
    fun fromCastList(cast: List<Cast?>?): String? {
        val type = object : TypeToken<List<Cast?>?>() {}.type
        return Gson().toJson(cast, type)
    }

    @TypeConverter
    fun toCastList(cast: String?): List<Cast>? {
        val type = object : TypeToken<List<Cast?>?>() {}.type
        return Gson().fromJson<List<Cast>>(cast, type)
    }

    @TypeConverter
    fun fromCrewList(crew: List<Crew?>?): String? {
        val type = object : TypeToken<List<Crew?>?>() {}.type
        return Gson().toJson(crew, type)
    }

    @TypeConverter
    fun toCrewList(crew: String?): List<Crew>? {
        val type = object : TypeToken<List<Crew?>?>() {}.type
        return Gson().fromJson<List<Crew>>(crew, type)
    }

    @TypeConverter
    fun fromAlsoKnownAsList(knownAs: List<AlsoKnownAs?>?): String? {
        val type = object : TypeToken<List<AlsoKnownAs?>?>() {}.type
        return Gson().toJson(knownAs, type)
    }

    @TypeConverter
    fun toAlsoKnownAsList(alsoKnownAs: String?): List<AlsoKnownAs>? {
        val type = object : TypeToken<List<AlsoKnownAs?>?>() {}.type
        return Gson().fromJson<List<AlsoKnownAs>>(alsoKnownAs, type)
    }

    @TypeConverter
    fun fromCreatedByList(createdBy: List<CreatedBy?>?): String? {
        val type = object : TypeToken<List<CreatedBy?>?>() {}.type
        return Gson().toJson(createdBy, type)
    }

    @TypeConverter
    fun toCreatedByList(createdBy: String?): List<CreatedBy>? {
        val type = object : TypeToken<List<CreatedBy?>?>() {}.type
        return Gson().fromJson<List<CreatedBy>>(createdBy, type)
    }

    @TypeConverter
    fun fromNetworkList(network: List<Network?>?): String? {
        val type = object : TypeToken<List<Network?>?>() {}.type
        return Gson().toJson(network, type)
    }

    @TypeConverter
    fun toNetworkList(network: String?): List<Network>? {
        val type = object : TypeToken<List<Network?>?>() {}.type
        return Gson().fromJson<List<Network>>(network, type)
    }

    @TypeConverter
    fun fromSeasonList(season: List<Season?>?): String? {
        val type = object : TypeToken<List<Season?>?>() {}.type
        return Gson().toJson(season, type)
    }

    @TypeConverter
    fun toSeasonList(season: String?): List<Season>? {
        val type = object : TypeToken<List<Season?>?>() {}.type
        return Gson().fromJson<List<Season>>(season, type)
    }

    @TypeConverter
    fun fromEpisodeRunTimeList(episodeRunTime: List<EpisodeRunTime?>?): String? {
        val type = object : TypeToken<List<EpisodeRunTime?>?>() {}.type
        return Gson().toJson(episodeRunTime, type)
    }

    @TypeConverter
    fun toEpisodeRunTimeList(episodeRuntime: String?): List<EpisodeRunTime>? {
        val type = object : TypeToken<List<EpisodeRunTime?>?>() {}.type
        return Gson().fromJson<List<EpisodeRunTime>>(episodeRuntime, type)
    }

    @TypeConverter
    fun fromEpisodesList(episodes: List<Episode?>?): String? {
        val type = object : TypeToken<List<Episode?>?>() {}.type
        return Gson().toJson(episodes, type)
    }

    @TypeConverter
    fun toEpisodesList(episodes: String?): List<Episode>? {
        val type = object : TypeToken<List<Episode?>?>() {}.type
        return Gson().fromJson<List<Episode>>(episodes, type)
    }

    @TypeConverter
    fun stringListToJson(value: List<String>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToStringList(value: String) = Gson().fromJson(value, Array<String>::class.java).toList()

}