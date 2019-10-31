package com.namnh.nfcsamples

import android.app.Application
import com.namnh.nfcsamples.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NFCApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NFCApplication)
            modules(appModule)
        }
    }
}