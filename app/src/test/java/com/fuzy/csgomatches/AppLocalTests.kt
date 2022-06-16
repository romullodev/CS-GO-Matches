package com.fuzy.csgomatches

import com.fuzy.csgomatches.domain.usecases.impl.GetMatchInDetailsImplTest
import com.fuzy.csgomatches.domain.usecases.impl.GetMatchesImplTest
import com.fuzy.csgomatches.domain.usecases.impl.GetOpponentDetailsImplTest
import com.fuzy.csgomatches.util.GlobalExtensionsTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    GlobalExtensionsTest::class,
    GetMatchesImplTest::class,
    GetMatchInDetailsImplTest::class,
    GetOpponentDetailsImplTest::class
)

class AppLocalTests