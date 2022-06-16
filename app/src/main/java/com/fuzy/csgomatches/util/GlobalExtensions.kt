package com.fuzy.csgomatches.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.fuzy.csgomatches.R
import com.fuzy.csgomatches.databinding.LayoutAllToolbarBinding
import com.fuzy.csgomatches.databinding.ViewAllInfoBinding
import com.fuzy.csgomatches.domain.entities.*
import com.fuzy.csgomatches.domain.errors.AppErrors
import com.fuzy.csgomatches.infra.model.*
import com.fuzy.csgomatches.util.GlobalConstants.Companion.CUSTOM_FORMAT
import com.fuzy.csgomatches.util.GlobalConstants.Companion.ISO_8601_FORMAT
import com.fuzy.csgomatches.util.GlobalConstants.Companion.NO_IMAGE
import com.fuzy.csgomatches.util.GlobalConstants.Companion.NO_NAME
import com.fuzy.csgomatches.util.GlobalConstants.Companion.ONE_DAY_IN_MILLISECONDS
import com.fuzy.csgomatches.util.GlobalConstants.Companion.ONE_WEEK_IN_MILLISECONDS
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import java.util.concurrent.TimeUnit


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

fun View.setVisibleOrGone(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun String?.formatTimeFromIso8601(ctx: Context): String = this?.run {
    val originalFormatter = SimpleDateFormat(ISO_8601_FORMAT, Locale.getDefault())
    originalFormatter.timeZone = TimeZone.getTimeZone("UTC")
    val targetFormatter = SimpleDateFormat(CUSTOM_FORMAT, Locale.getDefault())
    val date: Date? = originalFormatter.parse(this)
    // Finding the absolute difference between
    // the two dates.time (in milli seconds)
    val difference = date!!.time - AppUtil.timeUtil.getCalendar().timeInMillis

    if (isMatchAfterOneWeek(difference)) {
        targetFormatter.format(date)
    } else {
        getFormattedDate(
            AppUtil.timeUtil.getCalendar().also { it.time = date },
            ctx,
            today = isMatchToday(difference)
        )
    }
} ?: String()

private fun isMatchToday(difference: Long) =
    AppUtil.timeUtil.getCalendar().let {
        it.get(Calendar.HOUR_OF_DAY) * 60 * 60 * 1000 +
                difference < ONE_DAY_IN_MILLISECONDS
    }

private fun isMatchAfterOneWeek(difference: Long) = AppUtil.timeUtil.getCalendar().let {
    it.get(Calendar.HOUR_OF_DAY) * 60 * 60 * 1000 +
            difference >= ONE_WEEK_IN_MILLISECONDS
}

fun getFormattedDate(cal: Calendar, ctx: Context, today: Boolean = false): String {
    val moment =
        if (today)
            ctx.getString(R.string.all_tday)
        else
            getWeekName(cal.get(Calendar.DAY_OF_WEEK), ctx)

    val hour = cal.get(Calendar.HOUR_OF_DAY)
    val min = cal.get(Calendar.MINUTE)
    return "${moment}, ${if (hour > 9) hour else "0$hour"}:${if (min > 9) min else "0$min"}"
}

fun getWeekName(dayOfWeek: Int, ctx: Context) =
    when (dayOfWeek) {
        Calendar.SUNDAY -> ctx.getString(R.string.all_day_of_week_sunday)
        Calendar.MONDAY -> ctx.getString(R.string.all_day_of_week_monday)
        Calendar.TUESDAY -> ctx.getString(R.string.all_day_of_week_tuesday)
        Calendar.WEDNESDAY -> ctx.getString(R.string.all_day_of_week_wednesday)
        Calendar.THURSDAY -> ctx.getString(R.string.all_day_of_week_thursday)
        Calendar.FRIDAY -> ctx.getString(R.string.all_day_of_week_friday)
        Calendar.SATURDAY -> ctx.getString(R.string.all_day_of_week_saturday)
        else -> String()
    }

fun Fragment.showInfoAlertDialog(
    message: String,
    buttonMessage: String? = null,
    actionPos: Runnable? = null
) {
    val dialog = MaterialAlertDialogBuilder(
        requireContext(),
        R.style.ThemeOverlay_CSGOMatches_MaterialAlertDialog
    ).create()
    val binding = ViewAllInfoBinding.inflate(LayoutInflater.from(context), null, false)
    binding.run {
        buttonMessage?.let { buttonAllInfoOk.text = it }
        textViewAllMsg.text = message
        buttonAllInfoOk.setOnClickListener {
            actionPos?.run()
            dialog.dismiss()
        }
        dialog.setView(root)
    }
    dialog.setCancelable(false)
    dialog.show()
}

fun LayoutAllToolbarBinding.setupToolbarWithNavController(
    frg: Fragment
) {
    frg.findNavController().let { navController ->
        AppBarConfiguration(navController.graph).let {
            this.toolbar.setupWithNavController(navController, it)
        }
    }
}