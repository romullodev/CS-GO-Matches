package com.fuzy.csgomatches.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Opponent(
    val opponent: Team
): Parcelable
