package com.fuzy.csgomatches.core

import com.fuzy.csgomatches.BaseApplication
import dagger.hilt.android.testing.CustomTestApplication

@CustomTestApplication(BaseApplication::class)
interface HiltTestApplication