package com.fuzy.csgomatches.external

import com.fuzy.csgomatches.infra.model.MatchResponse
import com.fuzy.csgomatches.infra.model.TeamResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(AppEndPoints.ENDPOINT_CS_GO_MATCHES)
    suspend fun getMatches(): List<MatchResponse>

    @GET(AppEndPoints.ENDPOINT_CS_GO_MATCHES)
    suspend fun getPagedMatches(
        @Query("sort") sort: String = "-scheduled_at",
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<MatchResponse>

    @GET(AppEndPoints.ENDPOINT_CS_GO_TEAMS)
    suspend fun getOpponentDetails(
        @Query("filter[id]") id: Int,
    ): List<TeamResponse>
}