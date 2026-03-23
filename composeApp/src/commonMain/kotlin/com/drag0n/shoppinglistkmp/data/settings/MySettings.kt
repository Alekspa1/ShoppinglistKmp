package com.drag0n.shoppinglistkmp.data.settings

import com.russhwolf.settings.Settings

class MySettings(private val settings: Settings) {

    fun saveKey(key: String) {
        settings.putString(KEY, key)
    }

    fun getKey(): String {
        return settings.getString(KEY, "")
    }
companion object{
    const val KEY = "auth_key"
}
}

