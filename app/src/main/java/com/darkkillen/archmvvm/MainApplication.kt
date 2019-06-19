package com.darkkillen.archmvvm

import android.app.Application
import timber.log.Timber
import com.darkkillen.archmvvm.di.appModule
import com.darkkillen.archmvvm.di.localSourceModule
import com.darkkillen.archmvvm.di.remoteModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        startKoin {
            androidContext(this@MainApplication)
            androidLogger()
            modules(listOf(
                appModule,
                localSourceModule,
                remoteModule
            ))
        }
    }

}