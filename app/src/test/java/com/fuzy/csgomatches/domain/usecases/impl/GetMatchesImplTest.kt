package com.fuzy.csgomatches.domain.usecases.impl

import com.fuzy.csgomatches.BaseTest
import com.fuzy.csgomatches.domain.entities.Match
import com.fuzy.csgomatches.domain.usecases.contracts.GetMatches
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetMatchesImplTest : BaseTest() {

    private val getMatchesUseCase: GetMatches by lazy {
        GetMatchesImpl(repository)
    }

    @Test
    fun `get matches successfully`(): Unit = runBlocking {
        val result: List<Match> = getMatchesUseCase()
        assertTrue(result.isNotEmpty())
    }
}