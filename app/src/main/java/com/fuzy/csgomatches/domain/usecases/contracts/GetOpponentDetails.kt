package com.fuzy.csgomatches.domain.usecases.contracts

import com.fuzy.csgomatches.domain.entities.Team

interface GetOpponentDetails {
    suspend operator fun invoke(slugOpponent: String): Team
}