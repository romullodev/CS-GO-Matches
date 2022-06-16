package com.fuzy.csgomatches.core

import com.fuzy.csgomatches.domain.entities.MatchStatusEnum
import com.fuzy.csgomatches.infra.model.*

class TestUtil {
    companion object {
        const val MATCHES_SIZE = 3
        const val PLAYERS_SIZE = 10

        fun getFakeMatches(): List<MatchResponse> {
            val orderHistories = arrayListOf<MatchResponse>()
            (0 until MATCHES_SIZE).forEach {
                orderHistories.add(getFakeMatchResponse().copy(it+1))
            }
            return orderHistories
        }

        private fun getFakeMatchResponse() = MatchResponse(
            id = 1,
            league = LeagueResponse(1, "", "League Name"),
            serie = SerieResponse(""),
            opponents = listOf(
                OpponentResponse(opponent = TeamResponse(1, "", "Team 1", "slug 1", getFakePlayers())),
                OpponentResponse(opponent = TeamResponse(2, "", "Tean 2", "slug 2", getFakePlayers()))
            ),
            status = MatchStatusEnum.RUNNING.value,
            scheduled_at = "2022-06-16T00:00:00Z"
        )

        fun getFakePlayers(): List<PlayerResponse> {
            val orderHistories = arrayListOf<PlayerResponse>()
            (0 until PLAYERS_SIZE).forEach {
                orderHistories.add(
                    getFakePlayerResponse().copy(
                    name = "Name ${it+1}",
                    first_name = "First ${it+1}",
                    last_name = "Second ${it+1}"
                ))
            }
            return orderHistories
        }

        private fun getFakePlayerResponse() = PlayerResponse("", "","","")
    }
}