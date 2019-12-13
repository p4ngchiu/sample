package com.example.cloneapp.common

import android.app.Application
import androidx.multidex.MultiDex
import com.example.cloneapp.data.preferences.StoreData

class App : Application() {
    val storeData: StoreData by lazy {
        StoreData(this)
    }

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        instance = this
    }

    companion object {
        private var instance: App? = null
        fun get(): App = instance ?: App()
    }
}