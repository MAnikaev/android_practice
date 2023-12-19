package com.itis.hw

import android.app.Application
import com.itis.hw.di.ServiceLocator

class MovieApp : Application() {
    override fun onCreate() {
        super.onCreate()
        ServiceLocator.initData(ctx = this)
    }
}