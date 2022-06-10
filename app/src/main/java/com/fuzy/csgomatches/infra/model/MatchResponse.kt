package com.fuzy.csgomatches.infra.model

data class MatchResponse(
    var league: LeagueResponse?,
    var serie: SerieResponse?,
    var opponents: List<OpponentResponse>?
)
