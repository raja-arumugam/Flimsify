package com.example.moviesdataapp.di.component

import javax.inject.Qualifier

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MovieAPI

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class CoroutineScopeIO
