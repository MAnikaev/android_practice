package com.itis.hw.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "movies", primaryKeys = ["id", "release_year", "title"],
    indices = [Index("id", unique = true)])
data class MovieEntity(
    var id: Int,
    var title: String,
    @ColumnInfo(name = "image_url")
    var imageUrl: String,
    var rating: Double,
    @ColumnInfo(name = "release_year")
    var releaseYear: Int,
    var description: String
) : Serializable