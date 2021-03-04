package com.example.themovies.ui.movies

import androidx.lifecycle.ViewModel
import com.example.themovies.domain.MoviesInteractor
import javax.inject.Inject

class MoviesViewModel @Inject constructor(
    private val moviesInteractor: MoviesInteractor,
) : ViewModel() {
}