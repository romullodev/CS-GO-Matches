package com.fuzy.csgomatches.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Player(
    val image: String,
    val fullName: String,
    val nickname: String,
): Parcelable
