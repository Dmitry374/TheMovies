package com.example.themovies.di

import androidx.lifecycle.ViewModel
import com.example.themovies.ui.movies.MoviesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MoviesViewModel::class)
    abstract fun bindGithubUsersViewModel(githubUsersViewModel: MoviesViewModel): ViewModel
}