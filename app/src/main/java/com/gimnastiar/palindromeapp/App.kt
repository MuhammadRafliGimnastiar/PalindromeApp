package com.gimnastiar.palindromeapp

import android.app.Application
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp
import java.util.Locale

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}