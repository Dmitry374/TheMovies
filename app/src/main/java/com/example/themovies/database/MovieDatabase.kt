package com.example.themovies.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.themovies.model.entity.NowPlayingMovieEntity
import com.example.themovies.model.entity.PopularMovieEntity

@Database(
    entities = [PopularMovieEntity::class, NowPlayingMovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
}