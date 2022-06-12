package com.fuzy.csgomatches.domain.usecases.impl

import com.fuzy.csgomatches.domain.entities.Match
import com.fuzy.csgomatches.domain.entities.Opponent
import com.fuzy.csgomatches.domain.usecases.contracts.GetMatchInDetails
import com.fuzy.csgomatches.domain.usecases.contracts.GetOpponentDetails
import javax.inject.Inject

class GetMatchInDetailsImpl @Inject constructor(
    private val getOpponentDetailsUseCase: GetOpponentDetails
): GetMatchInDetails {
    override suspend fun invoke(match: Match): Match {
        val firstOpponent = getOpponentDetailsUseCase(match.opponents[0].opponent.slug)
        val secondOpponent = getOpponentDetailsUseCase(match.opponents[1].opponent.slug)
        return match.copy(
            opponents = listOf(
                Opponent(opponent = firstOpponent),
                Opponent(opponent = secondOpponent),
            )
        )
    }
}
