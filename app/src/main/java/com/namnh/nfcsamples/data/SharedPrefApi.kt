package com.namnh.nfcsamples.data

import android.content.SharedPreferences

/**
 * Created by minhb on 08/10/2019.
 */
interface SharedPrefApi{
    fun <T> put(key: String, data: T)
    fun <T> get(key: String, type: Class<T>, default: T? = null): T
    fun <T> putList(key: String, list: List<T>)
    fun <T> getList(key: String, clazz: Class<T>): List<T>?
    fun removeKey(key: String)
    fun clear()

    fun registerOnSharedPreferenceChangeListener(
        listener: SharedPreferences.OnSharedPreferenceChangeListener
    )

    fun unregisterOnSharedPreferenceChangeListener(
        listener: SharedPreferences.OnSharedPreferenceChangeListener
    )
}