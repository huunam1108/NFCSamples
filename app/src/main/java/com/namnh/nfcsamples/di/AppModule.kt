package com.namnh.nfcsamples.di

import com.google.gson.Gson
import com.namnh.nfcsamples.data.SharedPrefApi
import com.namnh.nfcsamples.data.SharedPrefApiImpl
import com.namnh.nfcsamples.features.NfcHelper
import org.koin.dsl.module

val appModule = module {
    single { NfcHelper(/*ApplicationContext*/get()) }
    single<SharedPrefApi> { SharedPrefApiImpl(get(), Gson()) }
}
