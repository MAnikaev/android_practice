package com.itis.hw.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "movie_user",
    foreignKeys = [
        ForeignKey(entity = UserEntity::class, parentColumns = ["email"], childColumns = ["user_email"]),
        ForeignKey(entity = MovieEntity::class, parentColumns = ["id"], childColumns = ["movie_id"])
    ],
    primaryKeys = ["movie_id", "user_email"]
)
data class UserMovieCrossRef(
    @ColumnInfo(name = "movie_id")
    var movieId: Int,
    @ColumnInfo(name = "user_email")
    var userEmail: String
)
