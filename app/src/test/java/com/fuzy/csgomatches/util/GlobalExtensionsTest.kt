package com.fuzy.csgomatches.util

import com.fuzy.csgomatches.domain.errors.AppErrors
import com.fuzy.csgomatches.infra.model.LeagueResponse
import com.fuzy.csgomatches.infra.model.MatchResponse
import com.fuzy.csgomatches.infra.model.PlayerResponse
import com.fuzy.csgomatches.infra.model.TeamResponse
import com.fuzy.csgomatches.util.GlobalConstants.Companion.NO_IMAGE
import com.fuzy.csgomatches.util.GlobalConstants.Companion.NO_NAME
import junit.framework.Assert.assertEquals
import org.junit.Test


class GlobalExtensionsTest {

    @Test
    fun `convert PlayerResponse to internal Player`() {
        val firstName = "Romulo"
        val lastName = "Silva"
        val playerResponse = PlayerResponse("", firstName, lastName, "rml")

        assertEquals("$firstName $lastName", playerResponse.toPlayerDomain().fullName)
        assertEquals(lastName, playerResponse.copy(first_name = null).toPlayerDomain().fullName)
        assertEquals(firstName, playerResponse.copy(last_name = null).toPlayerDomain().fullName)
        assertEquals(NO_NAME, playerResponse.copy(first_name = null, last_name = null).toPlayerDomain().fullName)
        assertEquals(NO_IMAGE, playerResponse.copy(image_url = null).toPlayerDomain().image)
        assertEquals(NO_NAME, playerResponse.copy(name = null).toPlayerDomain().nickname)
    }

    @Test
    fun `convert TeamResponse to internal Team`() {
        val teamResponse = TeamResponse(0, "","", "", listOf())

        try {
            teamResponse.copy(id = null).toTeamDomain()
        }catch (e: Exception){
            assert(e is AppErrors.TeamWithNoId)
        }

        try {
            teamResponse.copy(slug = null).toTeamDomain()
        }catch (e: Exception){
            assert(e is AppErrors.TeamWithNoSlug)
        }
    }

    @Test
    fun `convert LeagueResponse to internal League`() {
        val leagueResponse = LeagueResponse(0, "","")
        try {
            leagueResponse.copy(id = null).toLeagueDomain()
        }catch (e: Exception){
            assert(e is AppErrors.LeagueWithNoId)
        }
    }

    @Test
    fun `convert MatchResponse to Match`() {
        val matchResponse = MatchResponse(
            0,
            TestUtil.getFakeLeagueResponse(),
            TestUtil.getFakeSerieResponse(),
            listOf(
                TestUtil.getFakeOpponentResponse(),
                TestUtil.getFakeOpponentResponse()
            ),
            status = null,
            scheduled_at = null
        )

        try {
            matchResponse.copy(id = null).toMatchDomain()
        }catch (e: Exception){
            assert(e is AppErrors.MatchWithNoId)
        }
        try {
            matchResponse.copy(league = null).toMatchDomain()
        }catch (e: Exception){
            assert(e is AppErrors.MatchWithNoLeague)
        }

        try {
            matchResponse.copy(serie = null).toMatchDomain()
        }catch (e: Exception){
            assert(e is AppErrors.MatchWithNoSerie)
        }

        try {
            matchResponse.copy(opponents = null).toMatchDomain()
        }catch (e: Exception){
            assert(e is AppErrors.MatchWithNoOpponent)
        }
    }
}