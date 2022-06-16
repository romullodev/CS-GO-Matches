package com.fuzy.csgomatches.core

import android.content.Context
import androidx.navigation.NavController
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.platform.app.InstrumentationRegistry
import com.fuzy.csgomatches.ui.MainActivity
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before

@HiltAndroidTest
abstract class BaseTest {
    protected lateinit var activityScenario: ActivityScenario<MainActivity>
    protected lateinit var navController: NavController
    protected val context: Context by lazy { InstrumentationRegistry.getInstrumentation().targetContext }
    private val mIdlingResource: IdlingResource by lazy {
        EspressoIdlingResource.getIdlingResource()
    }

    @Before
    open fun setup() {
        IdlingRegistry.getInstance().register(mIdlingResource)
    }

    @After
    open fun teardown() {
        IdlingRegistry.getInstance().unregister(mIdlingResource)
        activityScenario.close()
    }

    abstract fun launchActivity()
}