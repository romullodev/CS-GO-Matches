package com.fuzy.csgomatches.external

import com.fuzy.csgomatches.domain.errors.AppErrors
import com.fuzy.csgomatches.domain.errors.GenericError
import com.fuzy.csgomatches.infra.datasource.RemoteDataSource
import com.fuzy.csgomatches.util.toMatchDomain
import com.fuzy.csgomatches.util.toTeamDomain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class RemoteDataSourceImpl constructor(
    private val apiService: ApiService,
    private val dispatcher: CoroutineDispatcher
) : RemoteDataSource {
    override suspend fun getMatches() = withContext(dispatcher) {
        try {
            apiService.getMatches()
                .toMatchDomain()
        } catch (e: Exception) {
            if (e is GenericError)
                throw e
            else
                throw AppErrors.UnknownError()
        }
    }

    override suspend fun getOpponentDetails(slugOpponent: String) = withContext(dispatcher) {
        try {
            apiService.getOpponentDetails(slugOpponent)
                .takeIf { it.size == 1 }?.let {
                    return@withContext it.toTeamDomain().first()
                }
            throw AppErrors.MoreThanOneOpponentFounded()
        } catch (e: Exception) {
            if (e is GenericError)
                throw e
            else
                throw AppErrors.UnknownError()
        }
    }
}