package com.guilherme.wheretowatch

import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.guilherme.wheretowatch.di.initializeKoin
import org.koin.android.ext.koin.androidContext

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        MobileAds.initialize(this@MainApplication)

        initializeKoin(
            config = {androidContext(this@MainApplication)}
        )
    }
}