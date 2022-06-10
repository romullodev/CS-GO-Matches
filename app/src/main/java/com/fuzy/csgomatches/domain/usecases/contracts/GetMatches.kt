package com.fuzy.csgomatches.domain.usecases.contracts

import com.fuzy.csgomatches.domain.entities.Match

interface GetMatches {
    suspend operator fun invoke(): List<Match>
}