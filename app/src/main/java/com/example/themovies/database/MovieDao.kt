package com.example.themovies.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.themovies.model.entity.NowPlayingMovieEntity
import com.example.themovies.model.entity.PopularMovieEntity
import io.reactivex.Single

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPopularMovies(popularMovies: List<PopularMovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNowPlayingMovies(nowPlayingMovies: List<NowPlayingMovieEntity>)

    @Query("SELECT * FROM popular_movie")
    fun getPopularMovies(): Single<List<PopularMovieEntity>>

    @Query("SELECT * FROM now_playing_movie")
    fun getNowPlayingMovies(): Single<List<NowPlayingMovieEntity>>
}