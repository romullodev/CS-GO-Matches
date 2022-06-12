package com.fuzy.csgomatches

import androidx.multidex.MultiDexApplication
import timber.log.Timber

open class BaseApplication: MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}