package com.fuzy.csgomatches.infra.model

data class TeamResponse(
    var id: Int?,
    var image_url: String?,
    var name: String?,
    var slug: String?,
    var players: List<PlayerResponse>?
)
