package com.itis.hw.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.itis.hw.data.entity.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE email = :email")
    fun getUserByEmail(email: String) : UserEntity?

    @Query("DELETE FROM users WHERE email = :email")
    fun deleteUser(email: String)

    @Query("UPDATE users SET phone = :newPhone WHERE email = :email")
    fun updateUserPhone(email: String, newPhone: String)

    @Query("UPDATE users SET password_hash = :newPasswordHash WHERE email = :email")
    fun updateUserPassword(email: String, newPasswordHash: String)
}