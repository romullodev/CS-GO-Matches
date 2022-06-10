package com.fuzy.csgomatches.infra.datasource

import com.fuzy.csgomatches.domain.entities.Match
import com.fuzy.csgomatches.domain.entities.Team

interface RemoteDataSource {
    suspend fun getMatches(): List<Match>
    suspend fun getOpponentDetails(slugOpponent: String): Team
}