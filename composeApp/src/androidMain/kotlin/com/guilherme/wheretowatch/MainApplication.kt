package com.guilherme.wheretowatch

import android.app.Application
import com.guilherme.wheretowatch.di.initializeKoin
import org.koin.android.ext.koin.androidContext

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initializeKoin(
            config = {androidContext(this@MainApplication)}
        )
    }
}