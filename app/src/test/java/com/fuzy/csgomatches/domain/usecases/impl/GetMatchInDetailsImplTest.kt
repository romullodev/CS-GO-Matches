package com.fuzy.csgomatches.domain.usecases.impl

import com.fuzy.csgomatches.BaseTest
import com.fuzy.csgomatches.domain.usecases.contracts.GetMatchInDetails
import com.fuzy.csgomatches.domain.usecases.contracts.GetMatches
import com.fuzy.csgomatches.domain.usecases.contracts.GetOpponentDetails
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class GetMatchInDetailsImplTest: BaseTest() {

    private val getMatchesUseCase: GetMatches by lazy {
        GetMatchesImpl(repository)
    }

    private val getOpponentDetailsUseCase: GetOpponentDetails by lazy {
        GetOpponentDetailsImpl(repository)
    }

    private val getMatchInDetailsUseCase: GetMatchInDetails by lazy {
        GetMatchInDetailsImpl(getOpponentDetailsUseCase)
    }

    @Test
    fun `get match in details successfully`(): Unit = runBlocking {
        val firstMatch = getMatchesUseCase().first()
        Assert.assertTrue(firstMatch.opponents.first().opponent.players.isEmpty())

        val result = getMatchInDetailsUseCase(firstMatch)
        Assert.assertTrue(result.opponents.first().opponent.players.isNotEmpty())
    }
}