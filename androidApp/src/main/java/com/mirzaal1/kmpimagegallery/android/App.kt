package com.mirzaal1.kmpimagegallery.android

import android.app.Application
import com.mirzaal1.kmpimagegallery.android.di.viewModelModule
import di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin{
            androidContext(this@App)
            module { viewModelModule }
        }
    }
}