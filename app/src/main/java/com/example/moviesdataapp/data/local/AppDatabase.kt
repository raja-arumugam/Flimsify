package com.example.moviesdataapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.moviesdataapp.data.model.*
import com.example.moviesdataapp.data.model.SeriesCast
import com.example.moviesdataapp.data.model.SeriesCrew

@Database(
    entities = [NowPlayingMovies::class, PopularMovies::class, TopRatedTV::class,
        PopularTv::class, ActionMovies::class, AiringTodayTv::class, MovieDetailsResponse::class,
        GenresList::class, ProductionCompanies::class, CastCrewListResponse::class,
        Cast::class, Crew::class, ActorDetailsResponse::class, AlsoKnownAs::class,
        ActorMovies::class, MovieVideos::class, SimilarMovie::class, TvDetailsResponse::class,
        CreatedBy::class, Network::class, Season::class, EpisodeRunTime::class,
        SimilarTv::class, SeasonDetails::class, Episode::class, SeriesCast::class, SeriesCrew::class,
        BackdropImages::class, ExternalIDs::class, ReviewList::class, UpcomingMovie::class,
        MovieGenreList::class, HollyWoodMoviesList::class, KollyWoodMoviesList::class, BollyWoodMoviesList::class,
        MollyWoodMoviesList::class, KoreanMoviesList::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun tvDao(): TvDao

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "cinema_db")
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)

                    }
                })
                .build()
        }

    }
}