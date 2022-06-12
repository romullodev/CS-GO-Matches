package com.fuzy.csgomatches.external

import androidx.lifecycle.MutableLiveData
import com.fuzy.csgomatches.infra.model.MatchResponse
import com.fuzy.csgomatches.infra.model.TeamResponse
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface ApiService {

    @GET(AppEndPoints.ENDPOINT_CS_GO_MATCHES)
    suspend fun getMatches(): List<MatchResponse>

    @GET(AppEndPoints.ENDPOINT_CS_GO_MATCHES)
    suspend fun getPagedMatches(
        @Query("sort") sort: String = "",
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<MatchResponse>

    @GET(AppEndPoints.ENDPOINT_CS_GO_TEAMS)
    suspend fun getOpponentDetails(
        @Query("search[slug]") slug: String,
        @Query("sort") sort: String = "",
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 50,
    ): List<TeamResponse>
}