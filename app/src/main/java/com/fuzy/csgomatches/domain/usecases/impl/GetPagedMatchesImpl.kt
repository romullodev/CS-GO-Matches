package com.fuzy.csgomatches.domain.usecases.impl

import androidx.lifecycle.LiveData
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.fuzy.csgomatches.domain.entities.Match
import com.fuzy.csgomatches.domain.repository.Repository
import com.fuzy.csgomatches.domain.usecases.contracts.GetPagedMatches
import javax.inject.Inject

class GetPagedMatchesImpl @Inject constructor(
    private val repository: Repository
) : GetPagedMatches {
    override fun invoke(): LiveData<PagingData<Match>> =
        repository.getMatchesObservable()
}