package com.fuzy.csgomatches.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class League(
    val id: Int,
    val image: String,
    val name: String,
): Parcelable
