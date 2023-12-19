package com.itis.hw.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Database
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.itis.hw.R
import com.itis.hw.data.MoviesDatabase
import com.itis.hw.utils.LocalDateTimeAdapter
import java.time.LocalDateTime

object ServiceLocator {
    private var dbInstance: MoviesDatabase? = null
    private var prefs: SharedPreferences? = null
    private var gson: Gson? = null

    fun initData(ctx: Context) {
        dbInstance = Room.databaseBuilder(ctx, MoviesDatabase::class.java, "movies.db")
            .build()

        gson = GsonBuilder()
            .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter())
            .create()

        prefs = ctx.getSharedPreferences(ctx.getString(R.string.prefs_name), Context.MODE_PRIVATE)
    }

    fun getDbInstance(): MoviesDatabase {
        return dbInstance ?: throw IllegalStateException("Db not initialized")
    }

    fun getPrefs() : SharedPreferences {
        return prefs ?: throw IllegalStateException("Prefs not initialized")
    }

    fun getGson() : Gson {
        return gson ?: throw IllegalStateException("Gson not initialized")
    }
}