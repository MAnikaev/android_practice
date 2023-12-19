package com.itis.hw.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    var email: String,
    var name: String,
    var phone: String,
    @ColumnInfo(name = "password_hash")
    var passwordHash: String
) : Serializable
