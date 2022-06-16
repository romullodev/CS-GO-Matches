package com.fuzy.csgomatches.util

import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.*

class GlobalExtensionsTest {

    private var day = "14"
    private val month = "06"
    private val year = "2022"
    private var hour = "02"
    private var min = "59"

    @Before
    fun before(){
        AppUtil.timeUtil = FakeTimeUtil().apply {
            day = 14 - 7
            month = 6 - 1
            year = 2022
            hour = 0
        }
    }

    @After
    fun teardown(){
        AppUtil.timeUtil = TimeUtilImpl()
    }

    @Test
    fun formatTimeZone_check_match_by_week_day() {
        assertEquals("Seg, 23:59", "$year-$month-${day}T${hour}:${min}:00Z".formatTimeFromIso8601(
            InstrumentationRegistry.getInstrumentation().targetContext
        ))

        day = "08"
        hour = "03"
        min = "00"
        assertEquals("Qua, 00:00", "$year-$month-${day}T${hour}:${min}:00Z".formatTimeFromIso8601(
            InstrumentationRegistry.getInstrumentation().targetContext
        ))
    }

    @Test
    fun formatTimeZone_check_match_by_date() {
        hour = "03"
        min = "00"
        assertEquals("14.06 00:00", "$year-$month-${day}T${hour}:${min}:00Z".formatTimeFromIso8601(
            InstrumentationRegistry.getInstrumentation().targetContext
        ))
    }

    @Test
    fun formatTimeZone_check_match_by_today() {
        day = "08"
        hour = "02"
        min = "59"

        assertEquals("Hoje, 23:59", "$year-$month-${day}T${hour}:${min}:00Z".formatTimeFromIso8601(
            InstrumentationRegistry.getInstrumentation().targetContext
        ))
    }
}

class FakeTimeUtil: TimeUtil {
    var year = 2022
    var month = 6
    var day = 1
    var hour = 0
    var min = 0
    var sec = 0
    var mili = 0
    var mTimeZone = "Brazil/East"
    override fun getCalendar(): Calendar =
        Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, day)
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, min)
            set(Calendar.SECOND, sec)
            set(Calendar.MILLISECOND, mili)
            timeZone = TimeZone.getTimeZone(mTimeZone)
        }
}