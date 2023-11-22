package com.itis.homework.models

interface CoroutinesDataListener {
    fun onDataRecieved(isStopOnBackground: Boolean)
}