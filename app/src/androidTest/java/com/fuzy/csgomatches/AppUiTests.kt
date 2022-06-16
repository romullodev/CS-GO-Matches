package com.fuzy.csgomatches

import com.fuzy.csgomatches.ui.details.MatchDetailsFragmentTest
import com.fuzy.csgomatches.ui.home.HomeFragmentTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    HomeFragmentTest::class,
    MatchDetailsFragmentTest::class
)

class AppUiTests