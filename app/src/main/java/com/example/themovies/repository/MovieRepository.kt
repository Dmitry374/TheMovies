package com.example.themovies.repository

import com.example.themovies.common.DataMapper
import com.example.themovies.model.domain.Movie
import com.example.themovies.network.ApiService
import io.reactivex.Single

class MovieRepository(
    private val apiService: ApiService,
    private val dataMapper: DataMapper
) {

    fun loadPopularMovies(): Single<List<Movie>> {
        return apiService.loadPopularMovies()
            .map { movieResponse ->
                dataMapper.listMoviesResultToMovieDomain(movieResponse.results)
            }
    }

    fun loadNowPlayingMovies(): Single<List<Movie>> {
        return apiService.loadNowPlayingMovies()
            .map { movieResponse ->
                dataMapper.listMoviesResultToMovieDomain(movieResponse.results)
            }
    }

}