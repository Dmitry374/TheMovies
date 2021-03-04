package com.example.themovies.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.themovies.domain.MoviesInteractor
import com.example.themovies.model.domain.Movie
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MoviesViewModel @Inject constructor(
    private val moviesInteractor: MoviesInteractor,
) : ViewModel() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    private val _movies by lazy { MutableLiveData<List<Movie>>() }
    val movies: LiveData<List<Movie>>
        get() = _movies

    private val _popularMoviesNetError by lazy { MutableLiveData<Throwable>() }
    val popularMoviesNetError: LiveData<Throwable>
        get() = _popularMoviesNetError

    init {
        loadPopularMovies()
    }

    private fun loadPopularMovies() {
        compositeDisposable.add(
            moviesInteractor.loadPopularMovies()
                .subscribe({ movies ->
                    _movies.value = movies
                }, {
                    _popularMoviesNetError.value = it
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}