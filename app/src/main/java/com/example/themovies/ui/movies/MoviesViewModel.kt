package com.example.themovies.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.themovies.domain.MoviesInteractor
import com.example.themovies.model.domain.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MoviesViewModel @Inject constructor(
    private val moviesInteractor: MoviesInteractor,
) : ViewModel() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    private val searchSubject = PublishSubject.create<String>()

    private val _popularMovies by lazy { MutableLiveData<List<Movie>>() }
    val popularMovies: LiveData<List<Movie>>
        get() = _popularMovies

    private val _popularMoviesNetError by lazy { MutableLiveData<Boolean>() }
    val popularMoviesNetError: LiveData<Boolean>
        get() = _popularMoviesNetError


    private val _nowPlayingMovies by lazy { MutableLiveData<List<Movie>>() }
    val nowPlayingMovies: LiveData<List<Movie>>
        get() = _nowPlayingMovies

    private val _nowPlayingMoviesNetError by lazy { MutableLiveData<Boolean>() }
    val nowPlayingMoviesNetError: LiveData<Boolean>
        get() = _nowPlayingMoviesNetError


    private val _searchMovies by lazy { MutableLiveData<List<Movie>>() }
    val searchMovies: LiveData<List<Movie>>
        get() = _searchMovies

    private val _searchMoviesNetError by lazy { MutableLiveData<Boolean>() }
    val searchMoviesNetError: LiveData<Boolean>
        get() = _searchMoviesNetError

    fun completeSearch() {
        searchSubject.onComplete()
    }

    fun searsNewMovie(newText: String) {
        searchSubject.onNext(newText)
    }

    fun searchMovies() {

        val searchMovieDisposable = searchSubject
            .filter { it.isNotEmpty() }
            .debounce(500, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMapSingle { query ->
                moviesInteractor.searchMovies(query)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ movies ->
                _searchMovies.value = movies

                _searchMoviesNetError.value = false
            }, {
                _searchMoviesNetError.value = true
            })

        compositeDisposable.add(searchMovieDisposable)
    }

    fun getPopularMoviesFromDB() {
        compositeDisposable.add(
            moviesInteractor.getPopularMovies()
                .subscribe { movies ->
                    _popularMovies.value = movies

                    loadPopularMovies()
                }
        )
    }

    fun getNowPlayingMoviesFromDB() {
        compositeDisposable.add(
            moviesInteractor.getNowPlayingMovies()
                .subscribe { movies ->
                    _nowPlayingMovies.value = movies

                    loadNowPlayingMovies()
                }
        )
    }

    private fun loadPopularMovies() {
        compositeDisposable.add(
            moviesInteractor.loadPopularMovies()
                .subscribe({ movies ->
                    _popularMovies.value = movies
                    _popularMoviesNetError.value = false
                }, {
                    _popularMoviesNetError.value = true
                })
        )
    }

    private fun loadNowPlayingMovies() {
        compositeDisposable.add(
            moviesInteractor.loadNowPlayingMovies()
                .subscribe({ movies ->
                    _nowPlayingMovies.value = movies
                    _nowPlayingMoviesNetError.value = false
                }, {
                    _nowPlayingMoviesNetError.value = true
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}