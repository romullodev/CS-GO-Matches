package com.fuzy.csgomatches.domain.usecases.impl

import com.fuzy.csgomatches.domain.repository.Repository
import com.fuzy.csgomatches.domain.usecases.contracts.GetMatches

class GetMatchesImpl constructor(
    private val repository: Repository
): GetMatches {
    override suspend fun invoke() = repository.getMatches()
}