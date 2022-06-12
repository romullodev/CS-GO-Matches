package com.fuzy.csgomatches.external

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fuzy.csgomatches.domain.entities.Match
import com.fuzy.csgomatches.domain.entities.MatchStatusEnum
import com.fuzy.csgomatches.util.toMatchDomain
import okio.IOException
import retrofit2.HttpException

class MatchPagingSource(private val apiService: ApiService) :
    PagingSource<Int, Match>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Match> {
        //for first case it will be null, then we can pass some default value, in our case it's 1
        val page = params.key ?: DEFAULT_PAGE_INDEX
        return try {
            val response = apiService.getPagedMatches(page = page, perPage = params.loadSize)
            LoadResult.Page(
                response.toMatchDomain().filter { it.opponents.size == 2 },
                prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Match>): Int? {
        return state.anchorPosition
    }

    companion object {
        const val DEFAULT_PAGE_INDEX = 1
        const val DEFAULT_PAGE_SIZE = 4
        const val DEFAULT_PREFETCH_DISTANCE = 1
    }
}