package com.fuzy.csgomatches.domain.usecases.contracts

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.fuzy.csgomatches.domain.entities.Match

interface GetPagedMatches {
    operator fun invoke(): LiveData<PagingData<Match>>
}