package com.my.rewardsproject.di

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application() {

    // The onCreate method is called when the application starts.
    // Any application-level initialization code can be placed here.
    override fun onCreate() {
        super.onCreate()

        // Application-specific logic can be added here if needed.
        // This is the entry point for the Application class before any activity is created.
    }
}