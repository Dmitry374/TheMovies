package com.example.themovies.common

import com.example.themovies.model.domain.Movie
import com.example.themovies.model.net.MovieResult

class DataMapper {

    fun listMoviesResultToMovieDomain(movieResultList: List<MovieResult>): List<Movie> {
        return movieResultList.toMovieDomainList()
    }

    private fun List<MovieResult>.toMovieDomainList() = this.map { it.toMovieDomain() }

    private fun MovieResult.toMovieDomain() = Movie(
        adult = this.adult,
        backdropPath = this.backdropPath,
        genreIds = this.genreIds,
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