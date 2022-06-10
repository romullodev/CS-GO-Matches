package com.fuzy.csgomatches.domain.repository

import com.fuzy.csgomatches.domain.entities.Match
import com.fuzy.csgomatches.domain.entities.Team

interface Repository {
    suspend fun getMatches(): List<Match>
    suspend fun getOpponentDetails(slugOpponent: String): Team
}