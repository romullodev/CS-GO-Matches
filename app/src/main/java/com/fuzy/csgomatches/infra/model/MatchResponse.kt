package com.fuzy.csgomatches.infra.model

data class MatchResponse(
    var id: Int?,
    var league: LeagueResponse?,
    var serie: SerieResponse?,
    var opponents: List<OpponentResponse>?,
    var status: String?,
    var scheduled_at: String?
)
