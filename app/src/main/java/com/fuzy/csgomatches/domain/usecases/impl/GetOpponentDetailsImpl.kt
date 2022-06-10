package com.fuzy.csgomatches.domain.usecases.impl

import com.fuzy.csgomatches.domain.repository.Repository
import com.fuzy.csgomatches.domain.usecases.contracts.GetOpponentDetails

class GetOpponentDetailsImpl constructor(
    private val repository: Repository
) : GetOpponentDetails {
    override suspend fun invoke(slugOpponent: String) =
        repository.getOpponentDetails(slugOpponent)
}