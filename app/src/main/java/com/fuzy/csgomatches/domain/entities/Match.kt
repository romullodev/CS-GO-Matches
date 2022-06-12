package com.fuzy.csgomatches.domain.entities

data class Match(
    val id: Int,
    val league: League,
    val serie: Serie,
    val opponents: List<Opponent>,
    val status: MatchStatusEnum,
    val scheduleAt: String
)