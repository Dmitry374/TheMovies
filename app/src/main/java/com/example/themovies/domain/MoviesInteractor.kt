package com.example.themovies.domain

import com.example.themovies.model.domain.Movie
import com.example.themovies.repository.MovieRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MoviesInteractor(
    private val movieRepository: MovieRepository
) {

    /**
     * Web
     */

    fun loadPopularMovies(): Single<List<Movie>> {
        return movieRepository.loadPopularMovies()
            .subscribeOn(Schedulers.io())
            .map { movies ->
                movieRepository.insertPopularMovies(movies)
                movies
            }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun loadNowPlayingMovies(): Single<List<Movie>> {
        return movieRepository.loadNowPlayingMovies()
            .subscribeOn(Schedulers.io())
            .map { movies ->
                movieRepository.insertNowPlayingMovies(movies)
                movies
            }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun searchMovies(query: String): Single<List<Movie>> {
        return movieRepository.searchMovies(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    /**
     * DB
     */

    fun getPopularMovies(): Single<List<Movie>> {
        return movieRepository.getPopularMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getNowPlayingMovies(): Single<List<Movie>> {
        return movieRepository.getNowPlayingMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}