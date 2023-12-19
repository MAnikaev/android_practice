package com.itis.hw.data.dao

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.itis.hw.data.entity.MovieEntity

@Dao
interface MovieDao {
    @Insert
    fun createMovie(movie: MovieEntity)

    @Query("UPDATE movies SET rating = CASE WHEN rating = 0 THEN :vote ELSE (rating + :vote) / 2 END WHERE id = :movieId")
    fun addVoteToMovie(movieId : Int, vote: Int)

    @Query("DELETE FROM movies WHERE id = :id")
    fun deleteMovie(id: Int)

    @Query("SELECT * FROM movies WHERE id = :id")
    fun getMovieById(id: Int) : MovieEntity?

    @Query("SELECT * FROM movies")
    fun getAllMovies() : MutableList<MovieEntity>

    @Query("SELECT rating FROM movies WHERE id = :movieId")
    fun getMovieRatingById(movieId: Int) : Double

    @Query("DELETE FROM movies")
    fun deleteAllMovies()
}