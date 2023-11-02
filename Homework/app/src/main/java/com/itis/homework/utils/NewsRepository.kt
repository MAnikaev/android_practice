package com.itis.homework.utils

import android.util.Range
import com.itis.homework.model.NewsData
import java.lang.Exception
import kotlin.random.Random
import kotlin.random.nextInt

object NewsRepository {
    private val news = mutableListOf<NewsData>(
        NewsData(
            id = 1,
            title = "Cats",
            description = "Awesome news about cats",
            imageUrl = "https://images.unsplash.com/photo-1608848461950-0fe51dfc41cb?auto=format&fit=crop&q=80&w=1000&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxleHBsb3JlLWZlZWR8Mnx8fGVufDB8fHx8fA%3D%3D",
            isFav = false
        ),
        NewsData(
            id = 2,
            title = "Dogs",
            description = "Awesome news about dogs",
            imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTdGosGxk_oZX6jD8KNismEvpvcLILh7BqQdg&usqp=CAU",
            isFav = false
        ),
        NewsData(
            id = 3,
            title = "Frogs",
            description = "Awesome news about frogs",
            imageUrl = "https://media.istockphoto.com/id/175397603/photo/frog.jpg?s=612x612&w=0&k=20&c=EMXlwg5SicJllr7gnSFUUjzwCGa1ciLjYD1bk8NvO2E=",
            isFav = false
        ),
        NewsData(
            id = 4,
            title = "Horses",
            description = "Awesome news about horses",
            imageUrl = "https://media.istockphoto.com/id/483797166/photo/andalusian-horse.jpg?s=612x612&w=0&k=20&c=2Xu8veyHqHMZP7OAJwkfihgJo0bsGhz0e1yGAGw7E4Y=",
            isFav = false
        ),
        NewsData(
            id = 5,
            title = "Spiders",
            description = "Awesome news about spiders",
            imageUrl = "https://media.istockphoto.com/id/92159944/photo/wolf-spider-with-young-03.jpg?s=1024x1024&w=is&k=20&c=jEkk9DXpthT1zYhQzhB2yVHdFTCAEanwkoJo1NnVfA8=",
            isFav = false
        ),
        NewsData(
            id = 6,
            title = "Elephants",
            description = "Awesome news about elephants",
            imageUrl = "https://media.istockphoto.com/id/479667835/ru/%D1%84%D0%BE%D1%82%D0%BE/%D1%84%D0%BE%D0%BD-%D1%81%D0%BB%D0%BE%D0%BD.jpg?s=612x612&w=0&k=20&c=A386tX1BASZC8yxT6I9pxAYCh4MLHkQnlKgsA71IEJk=",
            isFav = false
        ),
        NewsData(
            id = 7,
            title = "Birds",
            description = "Awesome news about birds",
            imageUrl = "https://media.istockphoto.com/id/1030966828/ru/%D1%84%D0%BE%D1%82%D0%BE/%D0%BC%D0%B0%D0%BB%D0%B8%D0%BD%D0%BE%D0%B2%D0%BA%D0%B0.jpg?s=612x612&w=0&k=20&c=Jmyfre5vVtRkZi7Ye__0n7DOspzAN4zTrtvgxg7OxD8=",
            isFav = false
        ),
        NewsData(
            id = 8,
            title = "Sharks",
            description = "Awesome news about sharks",
            imageUrl = "https://media.istockphoto.com/id/1165916494/ru/%D1%84%D0%BE%D1%82%D0%BE/%D0%B4%D0%B0%D0%B9%D0%B2%D0%B8%D0%BD%D0%B3-%D0%B2-%D0%BA%D0%BB%D0%B5%D1%82%D0%BA%D0%B5-%D1%81-%D0%B1%D0%BE%D0%BB%D1%8C%D1%88%D0%B8%D0%BC%D0%B8-%D0%B1%D0%B5%D0%BB%D1%8B%D0%BC%D0%B8-%D0%B0%D0%BA%D1%83%D0%BB%D0%B0%D0%BC%D0%B8-%D0%B2-%D0%B3%D0%B2%D0%B0%D0%B4%D0%B5%D0%BB%D1%83%D0%BF%D0%B5-%D0%BC%D0%B5%D0%BA%D1%81%D0%B8%D0%BA%D0%B0.jpg?s=612x612&w=0&k=20&c=L7OjdxIQC35-Za31dgMlhFBgtQXLbu2ZfKy2ot_vnKU=",
            isFav = false
        ),
        NewsData(
            id = 9,
            title = "Wolfs",
            description = "Awesome news about wolfs",
            imageUrl = "https://media.istockphoto.com/id/177794699/ru/%D1%84%D0%BE%D1%82%D0%BE/%D1%81%D0%B5%D1%80%D1%8B%D0%B9-%D0%B2%D0%BE%D0%BB%D0%BA-%D0%BF%D0%BE%D1%80%D1%82%D1%80%D0%B5%D1%82.jpg?s=612x612&w=0&k=20&c=zTMmfuCxFPLGdvQkmS3Xb6MVY7E-wk5nf3lnuVbTc4Q=",
            isFav = false
        ),
        NewsData(
            id = 10,
            title = "Lions",
            description = "Awesome news about lions",
            imageUrl = "https://media.istockphoto.com/id/487811195/ru/%D1%84%D0%BE%D1%82%D0%BE/%D0%BC%D1%83%D0%B6%D1%81%D0%BA%D0%BE%D0%B9-%D0%B6%D0%B5%D0%BD%D1%81%D0%BA%D0%B8%D0%B9-lions.jpg?s=612x612&w=0&k=20&c=ywcJCtL1AYwSQ7gFR9uWP-t4zVLyVlVydsmqkhZu-Yk=",
            isFav = false
        ),
        NewsData(
            id = 11,
            title = "Tigers",
            description = "Awesome news about tigers",
            imageUrl = "https://media.istockphoto.com/id/1218694103/ru/%D1%84%D0%BE%D1%82%D0%BE/%D0%BA%D0%BE%D1%80%D0%BE%D0%BB%D0%B5%D0%B2%D1%81%D0%BA%D0%B8%D0%B9-%D1%82%D0%B8%D0%B3%D1%80-%D0%B8%D0%B7%D0%BE%D0%BB%D0%B8%D1%80%D0%BE%D0%B2%D0%B0%D0%BD%D1%8B-%D0%BD%D0%B0-%D0%B1%D0%B5%D0%BB%D0%BE%D0%BC-%D1%84%D0%BE%D0%BD%D0%B5-%D0%BE%D1%82%D1%81%D0%B5%D1%87%D0%B5%D0%BD%D0%B8%D1%8F-%D0%BF%D1%83%D1%82%D1%8C-%D0%B2%D0%BA%D0%BB%D1%8E%D1%87%D0%B5%D0%BD-%D1%82%D0%B8%D0%B3%D1%80-%D1%81%D0%BC%D0%BE%D1%82%D1%80%D0%B8%D1%82-%D0%BD%D0%B0-%D1%81%D0%B2%D0%BE%D1%8E-%D0%B4%D0%BE%D0%B1%D1%8B%D1%87%D1%83.jpg?s=2048x2048&w=is&k=20&c=McpIw7pQummXvFc8Wv5NrhyGaL50oSGwNGu6Rbaf72k=",
            isFav = false
        ),
        NewsData(
            id = 12,
            title = "Goats",
            description = "Awesome news about goats",
            imageUrl = "https://media.istockphoto.com/id/855970482/ru/%D1%84%D0%BE%D1%82%D0%BE/%D0%BA%D0%BE%D0%B7%D0%B0.jpg?s=612x612&w=0&k=20&c=kvt4uOquqpqI27Zda97kdy-ube_nBMROJEwSJgx0DqM=",
            isFav = false
        ),
        NewsData(
            id = 13,
            title = "Cows",
            description = "Awesome news about cows",
            imageUrl = "https://media.istockphoto.com/id/106531788/ru/%D1%84%D0%BE%D1%82%D0%BE/%D0%B2%D0%B8%D0%B4-%D1%81%D0%B1%D0%BE%D0%BA%D1%83-%D0%B3%D0%BE%D0%BB%D1%8C%D1%88%D1%82%D0%B5%D0%B9%D0%BD-%D0%BA%D0%BE%D1%80%D0%BE%D0%B2%D0%B0-5-%D0%BB%D0%B5%D1%82-%D1%81%D1%82%D0%BE%D1%8F.jpg?s=612x612&w=0&k=20&c=mp8lQe5VI4f4HhJ0A4lti6GSW_9KoADjyXgzMaoR7UE=",
            isFav = false
        ),
        NewsData(
            id = 14,
            title = "Chickens",
            description = "Awesome news about chickens",
            imageUrl = "https://media.istockphoto.com/id/1217649450/ru/%D1%84%D0%BE%D1%82%D0%BE/%D0%BA%D1%83%D1%80%D0%B8%D1%86%D0%B0-%D0%B8%D0%BB%D0%B8-%D0%BA%D1%83%D1%80%D0%B8%D1%86%D0%B0-%D0%BD%D0%B0-%D0%B7%D0%B5%D0%BB%D0%B5%D0%BD%D0%BE%D0%BC-%D0%BB%D1%83%D0%B3%D1%83.jpg?s=612x612&w=0&k=20&c=fjbKAT1X3_PHIJMD9q69gNe2kQ4YGFvekt8z8q78Ees=",
            isFav = false
        ),
        NewsData(
            id = 15,
            title = "Gooses",
            description = "Awesome news about gooses",
            imageUrl = "https://media.istockphoto.com/id/1175699309/ru/%D1%84%D0%BE%D1%82%D0%BE/%D0%B4%D0%B2%D0%B0-%D0%BF%D0%BE%D0%BC%D0%BE%D1%80%D1%81%D0%BA%D0%B8%D1%85-%D0%B3%D1%83%D1%81%D1%8F.jpg?s=612x612&w=0&k=20&c=qI8AWjqK0vC0E7ltxLRvHfkVgACYnOuivh2R4qKCoGA=",
            isFav = false
        ),
    )

    fun getNews(count: Int?): MutableList<NewsData> {
        if (count == null || count < 0) {
                throw Exception()
        }

        val newsList = mutableListOf<NewsData>()

        for (i in 0 until count) {
            newsList.add(news[Random.nextInt(0, 15)].copy().apply {
                id = i
            })
        }
        return newsList
    }

    fun addNews(count: Int, oldList: MutableList<NewsData>) : MutableList<NewsData> {
        val newList = mutableListOf<NewsData>()
        newList.addAll(oldList)
        for(i in 0 until count) {
            val index = Random.nextInt(0, newList.size)
            newList.add(index, news[index % news.size])
        }

        return newList
    }

}