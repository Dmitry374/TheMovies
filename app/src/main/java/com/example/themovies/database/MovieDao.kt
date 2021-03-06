package com.example.themovies.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.themovies.model.entity.PopularMovieEntity

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPopularMovies(popularMovies: List<PopularMovieEntity>)
}