package com.example.themovies.network

import com.example.themovies.model.net.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    fun loadPopularMovies(
        @Query("page") page: Int = 1
    ): Single<MovieResponse>
}