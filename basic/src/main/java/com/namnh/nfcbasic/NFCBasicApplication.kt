package com.namnh.nfcbasic

import android.app.Application
import com.namnh.nfchelper.di.helperModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NFCBasicApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NFCBasicApplication)
            modules(helperModule)
        }
    }
}
