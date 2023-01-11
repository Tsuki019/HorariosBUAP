package com.prvt.horariosbuap.core

import android.content.Context

class Preferences(val context: Context) {

    val DB_NAME = "DB_Preferences"
    val DB_DARKTHEME = "DB_darkTheme_mode"

    val prefs = context.getSharedPreferences(DB_NAME, 0)

    fun saveTheme(darkTheme : Boolean){
        prefs.edit().putBoolean(DB_DARKTHEME, darkTheme).apply()
    }

    fun getTheme(): Boolean{
        return prefs.getBoolean(DB_DARKTHEME, false)
    }
}