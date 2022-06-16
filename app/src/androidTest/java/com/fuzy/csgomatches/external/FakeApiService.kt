package com.fuzy.csgomatches.external

import com.fuzy.csgomatches.core.TestUtil
import com.fuzy.csgomatches.di.IoDispatcher
import com.fuzy.csgomatches.domain.errors.AppErrors
import com.fuzy.csgomatches.infra.model.MatchResponse
import com.fuzy.csgomatches.infra.model.TeamResponse
import com.fuzy.csgomatches.core.wrapEspressoIdlingResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class FakeApiService @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher
): ApiService {

    var hasMatches = false
    var hasPlayers = false
    var throwUnknownError = false
    var throwTeamWithNoId = false
    var throwTeamWithNoSlug = false
    var throwLeagueWithNoId = false
    var throwMatchWithNoId = false
    var throwMatchWithNoLeague = false
    var throwMatchWithNoSerie = false
    var throwMatchWithNoOpponent = false
    var throwMoreThanOneOpponentFounded = false
    var throwErrorConnection = false

    fun clearAllFlags(){
        hasMatches = false
        hasPlayers = false
        throwUnknownError = false
        throwTeamWithNoId = false
        throwTeamWithNoSlug = false
        throwLeagueWithNoId = false
        throwMatchWithNoId = false
        throwMatchWithNoLeague = false
        throwMatchWithNoSerie = false
        throwMatchWithNoOpponent = false
        throwMoreThanOneOpponentFounded = false
        throwErrorConnection = false
    }

    override suspend fun getMatches(): List<MatchResponse> {
        return wrapEspressoIdlingResource {
            return@wrapEspressoIdlingResource withContext(dispatcher) {
                if(throwUnknownError)
                    throw AppErrors.UnknownError()

                if(throwMatchWithNoId)
                    throw AppErrors.MatchWithNoId()

                if(throwMatchWithNoLeague)
                    throw AppErrors.MatchWithNoLeague()

                if(throwMatchWithNoSerie)
                    throw AppErrors.MatchWithNoSerie()

                if(throwMatchWithNoOpponent)
                    throw AppErrors.MatchWithNoOpponent()

                if(hasMatches)
                    return@withContext TestUtil.getFakeMatches()

                return@withContext listOf()
            }
        }
    }

    override suspend fun getPagedMatches(
        sort: String,
        page: Int,
        perPage: Int
    ): List<MatchResponse> {
        return wrapEspressoIdlingResource {
            return@wrapEspressoIdlingResource withContext(dispatcher) {
                if(throwErrorConnection)
                    throw HttpException(Response.error<ResponseBody>(500 ,
                    "some content".toResponseBody("plain/text".toMediaTypeOrNull())
                ))

                return@withContext TestUtil.getFakeMatches()
            }
        }
    }

    override suspend fun getOpponentDetails(id: Int): List<TeamResponse> {
        return wrapEspressoIdlingResource {
            return@wrapEspressoIdlingResource withContext(dispatcher) {
                if(throwMoreThanOneOpponentFounded)
                    throw AppErrors.MoreThanOneOpponentFounded()
                if(throwUnknownError)
                    throw AppErrors.UnknownError()

                if(throwTeamWithNoId)
                    throw AppErrors.TeamWithNoId()

                if(throwTeamWithNoSlug)
                    throw AppErrors.TeamWithNoSlug()

                return@withContext listOf(
                    TeamResponse(1, "", "Team 1", "slug 1", TestUtil.getFakePlayers()),
                )
            }
        }
    }
}