package com.example.themovies.di

import com.example.themovies.ui.movies.MoviesFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class, ViewModelBuilderModule::class])
interface AppComponent {

    fun inject(moviesFragment: MoviesFragment)
}