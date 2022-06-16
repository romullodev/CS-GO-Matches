package com.fuzy.csgomatches.domain.usecases.impl

import com.fuzy.csgomatches.domain.repository.Repository
import com.fuzy.csgomatches.domain.usecases.contracts.GetOpponentDetails
import javax.inject.Inject

class GetOpponentDetailsImpl @Inject constructor(
    private val repository: Repository
) : GetOpponentDetails {
    override suspend fun invoke(id: Int) =
        repository.getOpponentDetails(id)
}