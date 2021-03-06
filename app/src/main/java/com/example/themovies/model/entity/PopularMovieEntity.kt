package com.example.themovies.model.entity

import androidx.room.Entity

@Entity(
    tableName = "popular_movie",
    primaryKeys = ["id", "title"]
)
class PopularMovieEntity(
    id: Int,
    adult: Boolean,
    backdropPath: String?,
    originalLanguage: String,
    originalTitle: String,
    overview: String,
    posterPath: String?,
    releaseDate: String?,
    title: String,
    video: Boolean,
    voteAverage: Double,
    voteCount: Int
) : MovieEntity(
    id,
    adult,
    backdropPath,
    originalLanguage,
    originalTitle,
    overview,
    posterPath,
    releaseDate,
    title,
    video,
    voteAverage,
    voteCount
)