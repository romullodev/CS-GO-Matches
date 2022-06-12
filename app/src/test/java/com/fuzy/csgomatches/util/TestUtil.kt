package com.fuzy.csgomatches.util

import com.fuzy.csgomatches.infra.model.LeagueResponse
import com.fuzy.csgomatches.infra.model.OpponentResponse
import com.fuzy.csgomatches.infra.model.SerieResponse
import com.fuzy.csgomatches.infra.model.TeamResponse

class TestUtil {
    companion object {
        fun getFakeLeagueResponse() = LeagueResponse(1, "", "")

        fun getFakeSerieResponse() = SerieResponse("")

        fun getFakeOpponentResponse() = OpponentResponse(getFakeTeamResponse())

        private fun getFakeTeamResponse() = TeamResponse(1, "","", "", listOf())
    }
}