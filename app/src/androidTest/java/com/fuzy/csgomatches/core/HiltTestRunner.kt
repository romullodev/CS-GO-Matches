package com.fuzy.csgomatches.core

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.fuzy.csgomatches.core.HiltTestApplication_Application

class HiltTestRunner : AndroidJUnitRunner(){
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, HiltTestApplication_Application::class.java.name, context)
    }
}