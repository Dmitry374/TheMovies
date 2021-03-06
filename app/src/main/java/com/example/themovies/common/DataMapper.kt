package com.example.themovies.common

import com.example.themovies.model.domain.Movie
import com.example.themovies.model.entity.MovieEntity
import com.example.themovies.model.entity.NowPlayingMovieEntity
import com.example.themovies.model.entity.PopularMovieEntity
import com.example.themovies.model.net.MovieResult

class DataMapper {

    /**
     * MovieResult list to Movie list
     */

    fun listMoviesResultToMovieDomain(movieResultList: List<MovieResult>): List<Movie> {
        return movieResultList.toMovieDomainList()
    }

    private fun List<MovieResult>.toMovieDomainList() = this.map { it.toMovieDomain() }

    private fun MovieResult.toMovieDomain() = Movie(
        adult = this.adult,
        backdropPath = this.backdropPath,
        id = this.id,
        originalLanguage = this.originalLanguage,
        originalTitle = this.originalTitle,
        overview = this.overview,
        posterPath = this.posterPath,
        releaseDate = this.releaseDate,
        title = this.title,
        video = this.video,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount
    )

    /**
     * Movie list to PopularMovieEntity list
     */

    fun listMoviesToMovieEntitiesList(movies: List<Movie>): List<PopularMovieEntity> {
        return movies.toMovieEntitiesList()
    }

    private fun List<Movie>.toMovieEntitiesList() = this.map { it.toMovieEntity() }

    private fun Movie.toMovieEntity() = PopularMovieEntity(
        id = this.id,
        adult = this.adult,
        backdropPath = this.backdropPath,
        originalLanguage = this.originalLanguage,
        originalTitle = this.originalTitle,
        overview = this.overview,
        posterPath = this.posterPath,
        releaseDate = this.releaseDate,
        title = this.title,
        video = this.video,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount
    )

    /**
     * Movie list to NowPlayingMovieEntity list
     */

    fun listMoviesToNowPlayingMovieEntitiesList(movies: List<Movie>): List<NowPlayingMovieEntity> {
        return movies.toNowPlayingMovieEntitiesList()
    }

    private fun List<Movie>.toNowPlayingMovieEntitiesList() =
        this.map { it.toNowPlayingMovieEntity() }

    private fun Movie.toNowPlayingMovieEntity() = NowPlayingMovieEntity(
        id = this.id,
        adult = this.adult,
        backdropPath = this.backdropPath,
        originalLanguage = this.originalLanguage,
        originalTitle = this.originalTitle,
        overview = this.overview,
        posterPath = this.posterPath,
        releaseDate = this.releaseDate,
        title = this.title,
        video = this.video,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount
    )

    /**
     * MovieEntity list to Movie list
     */

    fun listMoviesEntitiesToMovieList(movies: List<MovieEntity>): List<Movie> {
        return movies.toMoviesList()
    }

    private fun List<MovieEntity>.toMoviesList() = this.map { it.toMovie() }

    private fun MovieEntity.toMovie() = Movie(
        adult = this.adult,
        backdropPath = this.backdropPath,
        id = this.id,
        originalLanguage = this.originalLanguage,
        originalTitle = this.originalTitle,
        overview = this.overview,
        posterPath = this.posterPath,
        releaseDate = this.releaseDate,
        title = this.title,
        video = this.video,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount
    )
}