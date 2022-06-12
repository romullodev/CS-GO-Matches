package com.fuzy.csgomatches.external

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.fuzy.csgomatches.di.IoDispatcher
import com.fuzy.csgomatches.domain.entities.Match
import com.fuzy.csgomatches.domain.errors.AppErrors
import com.fuzy.csgomatches.domain.errors.GenericError
import com.fuzy.csgomatches.external.MatchPagingSource.Companion.DEFAULT_PAGE_SIZE
import com.fuzy.csgomatches.external.MatchPagingSource.Companion.DEFAULT_PREFETCH_DISTANCE
import com.fuzy.csgomatches.infra.datasource.RemoteDataSource
import com.fuzy.csgomatches.util.toMatchDomain
import com.fuzy.csgomatches.util.toTeamDomain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
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

    override fun getMatchesObservable(): LiveData<PagingData<Match>> {
        return Pager(
            config = getDefaultPageConfig(),
            pagingSourceFactory = { MatchPagingSource(apiService) }
        ).liveData
    }

    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(
            pageSize = DEFAULT_PAGE_SIZE,
            enablePlaceholders = true,
            initialLoadSize = DEFAULT_PAGE_SIZE,
            prefetchDistance = DEFAULT_PREFETCH_DISTANCE
        )
    }
}