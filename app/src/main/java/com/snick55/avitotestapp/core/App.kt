package com.snick55.avitotestapp.core

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {
}

fun log(string: String)= Log.d("TAG",string)