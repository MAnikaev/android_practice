package com.itis.hw.models

import com.itis.hw.data.entity.UserEntity
import java.io.Serializable
import java.time.LocalDateTime

data class Session(
    val userEmail: String,
    val expires: LocalDateTime
) : Serializable