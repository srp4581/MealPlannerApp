package com.srp4581.mealplannerapp

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

/**
 * Helper that stores a key-value pair in a simple form of
 * local storage (SharedPreferences)
 * */
object StorageUtils {
    const val SHARED_PREFERENCE_FILE_NAME = "meal_data"

    fun getString(activity: Activity, key: String, default: String? = null) =
        getSharedPreferences(activity).getString(key, default)

    fun getLong(activity: Activity, key: String, default: Long = -1): Long =
        getSharedPreferences(activity).getLong(key, default)

    fun storeString(activity: Activity, key: String, value: String) {
        getSharedPreferences(activity).edit { putString(key, value) }
    }

    fun storeLong(activity: Activity, key: String, value: Long) {
        getSharedPreferences(activity).edit { putLong(key, value) }

    }


    private fun getSharedPreferences(activity: Activity): SharedPreferences {
        return activity.getSharedPreferences(SHARED_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE)
    }
}