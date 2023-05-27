package com.example.moviesdataapp.di.component

import android.app.Application
import com.example.moviesdataapp.BuildConfig
import com.example.moviesdataapp.data.api.APIService
import com.example.moviesdataapp.data.api.AuthInterceptor
import com.example.moviesdataapp.data.local.AppDatabase
import com.example.moviesdataapp.data.repository.MovieApiRemoteSource
import com.example.moviesdataapp.data.repository.TvApiRemoteSource
import com.example.moviesdataapp.di.modules.ViewModelModule
import com.example.moviesdataapp.ui.viewmodel.MovieDetailsViewModel
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class, CoreDataModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideAPIService(
        @MovieAPI okhttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ) = provideService(okhttpClient, converterFactory, APIService::class.java)

    private fun <T> provideService(
        okhttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory, clazz: Class<T>
    ): T {
        return createRetrofit(okhttpClient, converterFactory).create(clazz)
    }

    private fun createRetrofit(
        okhttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okhttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

    @MovieAPI
    @Provides
    fun providePrivateOkHttpClient(
        upstreamClient: OkHttpClient
    ): OkHttpClient {
        return upstreamClient.newBuilder()
            .addInterceptor(AuthInterceptor(BuildConfig.AUTH_TOKEN)).build()
    }

    @CoroutineScopeIO
    @Provides
    fun provideCoroutineScopeIO() = CoroutineScope(Dispatchers.IO)

    @Singleton
    @Provides
    fun provideDb(app: Application) = AppDatabase.getInstance(app)

    @Singleton
    @Provides
    fun provideMovieDao(db: AppDatabase) = db.movieDao()

    @Singleton
    @Provides
    fun provideTvDao(db: AppDatabase) = db.tvDao()

    @Singleton
    @Provides
    fun provideMovieAPIRemoteDataSource(apiService: APIService)
            = MovieApiRemoteSource(apiService)

    @Singleton
    @Provides
    fun provideTvAPIRemoteDataSource(apiService: APIService)
            = TvApiRemoteSource(apiService)

}