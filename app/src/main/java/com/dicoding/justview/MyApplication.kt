package com.dicoding.justview

import android.app.Application
import com.dicoding.justview.core.di.databaseModule
import com.dicoding.justview.core.di.networkModule
import com.dicoding.justview.core.di.repositoryModule
import com.dicoding.justview.di.useCaseModule
import com.dicoding.justview.di.viewModelModule
import com.dicoding.justview.utils.ReleaseTree
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

@Suppress("unused")
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(applicationContext)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule,
                )
            )
        }
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }
    }
}