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

    private val _popularMovies by lazy { MutableLiveData<List<Movie>>() }
    val popularMovies: LiveData<List<Movie>>
        get() = _popularMovies

    private val _popularMoviesNetError by lazy { MutableLiveData<Throwable>() }
    val popularMoviesNetError: LiveData<Throwable>
        get() = _nowPlayingMoviesNetError


    private val _nowPlayingMovies by lazy { MutableLiveData<List<Movie>>() }
    val nowPlayingMovies: LiveData<List<Movie>>
        get() = _nowPlayingMovies

    private val _nowPlayingMoviesNetError by lazy { MutableLiveData<Throwable>() }
    val nowPlayingMoviesNetError: LiveData<Throwable>
        get() = _nowPlayingMoviesNetError

    init {
        loadPopularMovies()
        loadNowPlayingMovies()
    }

    private fun loadPopularMovies() {
        compositeDisposable.add(
            moviesInteractor.loadPopularMovies()
                .subscribe({ movies ->
                    _popularMovies.value = movies
                }, {
                    _popularMoviesNetError.value = it
                })
        )
    }

    private fun loadNowPlayingMovies() {
        compositeDisposable.add(
            moviesInteractor.loadNowPlayingMovies()
                .subscribe({ movies ->
                    _nowPlayingMovies.value = movies
                }, {
                    _nowPlayingMoviesNetError.value = it
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}