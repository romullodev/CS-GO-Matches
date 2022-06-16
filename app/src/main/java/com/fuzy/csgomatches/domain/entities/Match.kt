package com.fuzy.csgomatches.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Match(
    val id: Int,
    val league: League,
    val serie: Serie,
    val opponents: List<Opponent>,
    val status: MatchStatusEnum,
    val scheduleAt: String
): Parcelable