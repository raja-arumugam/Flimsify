package com.example.moviesdataapp.di.modules

import com.example.moviesdataapp.ui.fragment.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeMovieListsFragment(): MoviesFragment

    @ContributesAndroidInjector
    abstract fun contributeTvShowListFragment(): TvShowsListFragment

    @ContributesAndroidInjector
    abstract fun contributeDetailsFragment(): DetailsFragment

    @ContributesAndroidInjector
    abstract fun contributeActorDetailsFragment(): ActorDetailsFragment

    @ContributesAndroidInjector
    abstract fun contributeVideoPlayerFragment(): VideoPlayerFragment

    @ContributesAndroidInjector
    abstract fun contributeTVSeasonFragment(): TvSeasonFragment

    @ContributesAndroidInjector
    abstract fun contributeImagePreviewFragment(): ImagePreviewFragment

    @ContributesAndroidInjector
    abstract fun contributeExploreFragment(): ExploreFragment

    @ContributesAndroidInjector
    abstract fun contributeGenreFragment(): GenreFragment

}