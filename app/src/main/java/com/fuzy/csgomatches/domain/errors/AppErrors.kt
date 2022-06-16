package com.fuzy.csgomatches.domain.errors

import com.fuzy.csgomatches.R

class AppErrors {

    class UnknownError : GenericError()

    class TeamWithNoId : GenericError() {
        override val resourceMsg: Int = R.string.team_with_no_id
    }

    class TeamWithNoSlug : GenericError() {
        override val resourceMsg: Int = R.string.team_with_no_id
    }

    class LeagueWithNoId : GenericError() {
        override val resourceMsg: Int = R.string.league_with_no_id
    }

    class MatchWithNoId : GenericError() {
        override val resourceMsg: Int = R.string.match_with_no_id
    }

    class MatchWithNoLeague : GenericError() {
        override val resourceMsg: Int = R.string.match_with_no_league
    }

    class MatchWithNoSerie : GenericError() {
        override val resourceMsg: Int = R.string.match_with_no_serie
    }

    class MatchWithNoOpponent : GenericError() {
        override val resourceMsg: Int = R.string.match_with_no_opponent
    }

    class MoreThanOneOpponentFounded : GenericError() {
        override val resourceMsg: Int = R.string.more_than_one_opponent_founded
    }
}