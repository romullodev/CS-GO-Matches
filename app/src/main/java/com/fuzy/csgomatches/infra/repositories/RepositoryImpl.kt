package com.fuzy.csgomatches.infra.repositories

import com.fuzy.csgomatches.domain.repository.Repository
import com.fuzy.csgomatches.infra.datasource.RemoteDataSource

class RepositoryImpl constructor(
    private val remoteDataSource: RemoteDataSource
): Repository {
    override suspend fun getMatches() = remoteDataSource.getMatches()
    override suspend fun getOpponentDetails(slugOpponent: String) =
        remoteDataSource.getOpponentDetails(slugOpponent)
}