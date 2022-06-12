package com.fuzy.csgomatches.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.fuzy.csgomatches.domain.entities.Match
import com.fuzy.csgomatches.domain.entities.Team

interface Repository {
    suspend fun getMatches(): List<Match>
    suspend fun getOpponentDetails(slugOpponent: String): Team
    fun getMatchesObservable(): LiveData<PagingData<Match>>
}