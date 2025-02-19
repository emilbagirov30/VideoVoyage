package com.emil.videovoyage.app

import android.app.Application
import com.emil.videovoyage.di.appModule
import com.emil.videovoyage.di.dataModule
import com.emil.videovoyage.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App: Application (){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(listOf(dataModule, domainModule, appModule))
        }
    }
}