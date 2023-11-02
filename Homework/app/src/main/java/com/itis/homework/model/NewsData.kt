package com.itis.homework.model

data class NewsData(
    var id: Int,
    val title: String,
    val description: String,
    val imageUrl: String,
    var isFav: Boolean
)
