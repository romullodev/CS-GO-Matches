package com.fuzy.csgomatches.domain.usecases.impl

import com.fuzy.csgomatches.BaseTest
import com.fuzy.csgomatches.domain.entities.Team
import com.fuzy.csgomatches.domain.usecases.contracts.GetOpponentDetails
import org.junit.Assert
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetOpponentDetailsImplTest: BaseTest() {
    private val getOpponentDetailsUseCase: GetOpponentDetails by lazy {
        GetOpponentDetailsImpl(repository)
    }

    @Test
    fun `get opponent details successfully`(): Unit = runBlocking {
        val slug = "websterz"
        val result: Team = getOpponentDetailsUseCase(slug)
        Assert.assertEquals(result.slug, slug)
    }
}