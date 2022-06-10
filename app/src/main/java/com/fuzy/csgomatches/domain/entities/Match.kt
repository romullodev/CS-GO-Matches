package com.fuzy.csgomatches.domain.entities

data class Match(
    val league: League,
    val serie: Serie,
    val opponents: List<Opponent>
)