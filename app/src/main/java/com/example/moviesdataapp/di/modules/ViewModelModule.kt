package com.example.moviesdataapp.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviesdataapp.di.component.ViewModelFactory
import com.example.moviesdataapp.di.component.ViewModelKey
import com.example.moviesdataapp.ui.viewmodel.*
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MoviesListViewModel::class)
    abstract fun bindMovieListViewModel(viewModel: MoviesListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TvListViewModel::class)
    abstract fun bindTvListViewModel(viewModel: TvListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailsViewModel::class)
    abstract fun bindMovieDetailsViewModel(viewModel: MovieDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ActorDetailsViewModel::class)
    abstract fun bindActorDetailsViewModel(viewModel: ActorDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SeasonDetailsViewModel::class)
    abstract fun bindSeasonDetailsViewModel(viewModel: SeasonDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ExploreViewModel::class)
    abstract fun bindExploreViewModel(viewModel: ExploreViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GenresMoviesViewModel::class)
    abstract fun bindGenresMoviesViewModel(viewModel: GenresMoviesViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}