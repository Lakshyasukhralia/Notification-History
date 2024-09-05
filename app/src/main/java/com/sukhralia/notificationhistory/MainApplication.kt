package com.sukhralia.notificationhistory

import android.app.Application
import com.sukhralia.notificationhistory.core.di.databaseModule
import com.sukhralia.notificationhistory.feature.tracker.di.homeModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(
                databaseModule(), homeModule(),
            ).allowOverride(true)
        }
    }

}