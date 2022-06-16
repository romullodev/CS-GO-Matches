package com.fuzy.csgomatches.domain.usecases.impl

import com.fuzy.csgomatches.domain.entities.Match
import com.fuzy.csgomatches.domain.entities.Opponent
import com.fuzy.csgomatches.domain.usecases.contracts.GetMatchInDetails
import com.fuzy.csgomatches.domain.usecases.contracts.GetOpponentDetails
import com.fuzy.csgomatches.util.GlobalConstants.Companion.FIRST_OPPONENT
import com.fuzy.csgomatches.util.GlobalConstants.Companion.SECOND_OPPONENT
import javax.inject.Inject

class GetMatchInDetailsImpl @Inject constructor(
    private val getOpponentDetailsUseCase: GetOpponentDetails
): GetMatchInDetails {
    override suspend fun invoke(match: Match): Match {
        val firstOpponent = getOpponentDetailsUseCase(match.opponents[FIRST_OPPONENT].opponent.id)
        val secondOpponent = getOpponentDetailsUseCase(match.opponents[SECOND_OPPONENT].opponent.id)
        return match.copy(
            opponents = listOf(
                Opponent(opponent = firstOpponent),
                Opponent(opponent = secondOpponent),
            )
        )
    }
}
