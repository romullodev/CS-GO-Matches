package com.fuzy.csgomatches.core

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider

inline fun<reified T : Activity?> launchMyMainActivity(): ActivityScenario<T> {
    val startActivityIntent = Intent.makeMainActivity(
        ComponentName(
            ApplicationProvider.getApplicationContext(),
            T::class.java
        )
    )
    return ActivityScenario.launch(startActivityIntent)
}