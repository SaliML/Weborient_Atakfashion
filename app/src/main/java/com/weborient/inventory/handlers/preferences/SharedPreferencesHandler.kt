package com.weborient.inventory.handlers.preferences

import android.content.Context

object SharedPreferencesHandler {
    inline fun <reified T> saveValue(context: Context, sharedPreferencesID: String, key: String, value: T){
        val sharedPrefEditor = context.getSharedPreferences(sharedPreferencesID, Context.MODE_PRIVATE).edit()

        when (value) {
            is String -> sharedPrefEditor.putString(key, value).apply()
            is Boolean -> sharedPrefEditor.putBoolean(key, value).apply()
            is Int -> sharedPrefEditor.putInt(key, value).apply()
            is Float -> sharedPrefEditor.putFloat(key, value).apply()
        }
    }

    inline fun <reified T> getValue(context: Context, sharedPreferencesID: String, key: String): T?{
        val sharedPrefEditor = context.getSharedPreferences(sharedPreferencesID, Context.MODE_PRIVATE)

        return when(T::class){
            Int::class-> sharedPrefEditor.getInt(key, -1) as T
            Boolean::class-> sharedPrefEditor.getBoolean(key, false) as T
            String::class-> sharedPrefEditor.getString(key, "") as T
            Float::class-> sharedPrefEditor.getFloat(key, -1.0F) as T
            else -> null
        }
    }
}