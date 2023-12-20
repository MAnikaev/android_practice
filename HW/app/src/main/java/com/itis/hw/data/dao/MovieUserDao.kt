package com.itis.hw.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.itis.hw.data.entity.MovieEntity
import com.itis.hw.data.entity.UserMovieCrossRef

@Dao
interface MovieUserDao {
    @Query("SELECT * FROM movies INNER JOIN movie_user ON movies.id = movie_user.movie_id WHERE movie_user.user_email = :email")
    fun getMoviesForUser(email: String): List<MovieEntity>

    @Insert
    fun insertUserMovieJoin(userMovieJoin: UserMovieCrossRef)

    @Delete
    fun deleteUserMovieJoin(userMovieJoin: UserMovieCrossRef)

    @Query("DELETE FROM movie_user WHERE movie_id = :movieId")
    fun deleteAllMovieCrossRefs(movieId: Int)

    @Query("SELECT movie_id FROM movie_user WHERE user_email = :email")
    fun getMovieIdsForUser(email: String) : List<Int>

    @Query("DELETE FROM movie_user WHERE user_email = :userEmail")
    fun deleteAllUserCrossRefs(userEmail: String)
}