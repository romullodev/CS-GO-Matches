package com.fuzy.csgomatches.infra.repositories

import androidx.paging.PagingConfig
import com.fuzy.csgomatches.domain.repository.Repository
import com.fuzy.csgomatches.infra.datasource.RemoteDataSource
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
): Repository {
    override suspend fun getMatches() = remoteDataSource.getMatches()

    override suspend fun getOpponentDetails(slugOpponent: String) =
        remoteDataSource.getOpponentDetails(slugOpponent)

    override fun getMatchesObservable() =
        remoteDataSource.getMatchesObservable()
}