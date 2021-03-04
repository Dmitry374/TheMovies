package com.example.themovies

import android.app.Application
import com.example.themovies.di.AppComponent
import com.example.themovies.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .build()
    }
}