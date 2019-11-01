package com.namnh.nfchelper.di

import com.google.gson.Gson
import com.namnh.nfchelper.data.SharedPrefApi
import com.namnh.nfchelper.data.SharedPrefApiImpl
import org.koin.dsl.module

val helperModule = module {
    single { com.namnh.nfchelper.NfcHelper(/*ApplicationContext*/get()) }
    single<SharedPrefApi> { SharedPrefApiImpl(get(), Gson()) }
}
