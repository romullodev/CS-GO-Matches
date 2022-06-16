package com.fuzy.csgomatches.domain.usecases.contracts

import com.fuzy.csgomatches.domain.entities.Match

interface GetMatchInDetails {
    suspend operator fun invoke(match: Match): Match
}