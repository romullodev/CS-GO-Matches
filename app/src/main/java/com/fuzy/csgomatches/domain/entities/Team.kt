package com.fuzy.csgomatches.domain.entities

data class Team(
    val id: Int,
    val image: String,
    val name: String,
    val slug: String,
    val players: List<Player>
)