package com.fuzy.csgomatches

import android.app.Application
import timber.log.Timber

//@HiltAndroidApp
class MainApplication : Application() {//,  Configuration.Provider{

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}