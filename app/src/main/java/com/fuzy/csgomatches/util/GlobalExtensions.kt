package com.fuzy.csgomatches.util

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.fuzy.csgomatches.domain.entities.*
import com.fuzy.csgomatches.domain.errors.AppErrors
import com.fuzy.csgomatches.infra.model.*
import com.fuzy.csgomatches.util.GlobalConstants.Companion.NO_IMAGE
import com.fuzy.csgomatches.util.GlobalConstants.Companion.NO_NAME
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


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
    id = id ?: throw AppErrors.MatchWithNoId(),
    league = league?.toLeagueDomain() ?: throw AppErrors.MatchWithNoLeague(),
    serie = serie?.toSerieDomain() ?: throw AppErrors.MatchWithNoSerie(),
    opponents = opponents?.toOpponentDomain() ?: throw AppErrors.MatchWithNoOpponent(),
    status = getMatchStatus(status),
    scheduleAt = scheduled_at ?: ""
)

fun getMatchStatus(status: String?): MatchStatusEnum {
    status.let {
        return when (it?.uppercase()?.trim()) {
            MatchStatusEnum.RUNNING.value -> MatchStatusEnum.RUNNING
            MatchStatusEnum.CANCELED.value -> MatchStatusEnum.CANCELED
            MatchStatusEnum.NOT_STARTED.value -> MatchStatusEnum.NOT_STARTED
            MatchStatusEnum.FINISHED.value -> MatchStatusEnum.FINISHED
            else -> MatchStatusEnum.NO_STATUS
        }
    }
}

fun View.setVisibleOrGone(isVisible: Boolean){
    visibility = if(isVisible) View.VISIBLE else View.GONE
}

fun Fragment.showToast(msg: String, duration: Int = Toast.LENGTH_SHORT){
    Toast.makeText(requireContext(), msg, duration).show()
}

fun String?.formatTime(): String = this?.run {
    val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    val date: Date? = originalFormat.parse(this)

    val myCal: Calendar = GregorianCalendar()
    myCal.time = date!!

    "Week: ${myCal[Calendar.DAY_OF_WEEK]} Day: ${myCal[Calendar.DAY_OF_MONTH]} Month: ${myCal[Calendar.MONTH] + 1} Year: ${myCal[Calendar.YEAR]} Hour: ${myCal[Calendar.HOUR]} Minute: ${myCal[Calendar.MINUTE]}"
} ?: ""

//    fun getFormattedMatchScheduling(scheduleAt: String): String {
//        return
//
//
////            val sourceFormat = SimpleDateFormat("dd/MM/yyyy' 'HH:mm", Locale.getDefault())
////            val parsed: Date? = sourceFormat.parse(scheduleAt)
////            parsed.