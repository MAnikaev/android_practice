package com.itis.hw.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.itis.hw.data.dao.MovieDao
import com.itis.hw.data.dao.MovieUserDao
import com.itis.hw.data.dao.UserDao
import com.itis.hw.data.entity.MovieEntity
import com.itis.hw.data.entity.UserEntity
import com.itis.hw.data.entity.UserMovieCrossRef

@Database(entities = [UserEntity::class, MovieEntity::class, UserMovieCrossRef::class], version = 1)
abstract class MoviesDatabase : RoomDatabase(){
    abstract fun userDao(): UserDao
    abstract fun movieDao(): MovieDao
    abstract fun movieUserDao(): MovieUserDao
}