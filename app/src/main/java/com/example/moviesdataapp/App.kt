package com.example.moviesdataapp

import android.app.Application
import com.example.moviesdataapp.di.component.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class App: Application(), HasAndroidInjector {

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        AppInjector.init(this)
    }
        override fun androidInjector(): AndroidInjector<Any> {
            return activityDispatchingAndroidInjector
    }

}