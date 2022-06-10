package com.fuzy.csgomatches.util

import com.fuzy.csgomatches.domain.entities.*
import com.fuzy.csgomatches.domain.errors.AppErrors
import com.fuzy.csgomatches.infra.model.*
import com.fuzy.csgomatches.util.GlobalConstants.Companion.NO_IMAGE
import com.fuzy.csgomatches.util.GlobalConstants.Companion.NO_NAME


fun List<PlayerResponse>.toPlayerDomain(): List<Player> = map {
    it.toPlayerDomain()
}

fun PlayerResponse.toPlayerDomain() = Player(
    image = image_url ?: GlobalConstants.NO_IMAGE,
    fullName = "${first_name ?: String()} ${last_name ?: String()}".trim().ifEmpty { NO_NAME },
    nickname = name ?: NO_NAME
)

fun List<TeamResponse>.toTeamDomain(): List<Team> = map {
    it.toTeamDomain()
}

fun TeamResponse.toTeamDomain() = Team(
    id = id ?: throw AppErrors.TeamWithNoId(),
    image = image_url ?: NO_IMAGE,
    name = name ?: NO_NAME,
    slug = slug ?: throw AppErrors.TeamWithNoSlug(),
    players = players?.toPlayerDomain() ?: listOf()
)

fun List<LeagueResponse>.toLeagueDomain(): List<League> = map {
    it.toLeagueDomain()
}

fun LeagueResponse.toLeagueDomain() = League(
    id = id ?: throw AppErrors.LeagueWithNoId(),
    image = image_url ?: NO_IMAGE,
    name = name ?: NO_NAME,
)

fun SerieResponse.toSerieDomain(): Serie = Serie(
    name = name ?: NO_NAME
)

fun List<OpponentResponse>.toOpponentDomain(): List<Opponent> = map {
    it.toOpponentDomain()
}

fun OpponentResponse.toOpponentDomain() = Opponent(this.opponent.toTeamDomain())

fun List<MatchResponse>.toMatchDomain(): List<Match> = map {
    it.toMatchDomain()
}

fun MatchResponse.toMatchDomain() = Match(
    league = league?.toLeagueDomain() ?: throw AppErrors.MatchWithNoLeague(),
    serie = serie?.toSerieDomain() ?: throw AppErrors.MatchWithNoSerie(),
    opponents = opponents?.toOpponentDomain() ?: throw AppErrors.MatchWithNoOpponent()
)