package com.fuzy.csgomatches.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Team(
    val id: Int,
    val image: String,
    val name: String,
    val slug: String,
    val players: List<Player>
): Parcelable