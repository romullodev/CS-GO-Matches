package com.fuzy.csgomatches.infra.datasource

import androidx.lifecycle.LiveData
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.fuzy.csgomatches.domain.entities.Match
import com.fuzy.csgomatches.domain.entities.Team

interface RemoteDataSource {
    suspend fun getMatches(): List<Match>
    suspend fun getOpponentDetails(id: Int): Team
    fun getMatchesObservable(): LiveData<PagingData<Match>>
}