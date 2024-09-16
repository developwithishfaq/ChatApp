package com.test.chatappcopy.base

import android.app.Application
import com.test.chatappcopy.di.SharedModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppClass : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(SharedModules)
            androidContext(applicationContext)
        }
    }
}