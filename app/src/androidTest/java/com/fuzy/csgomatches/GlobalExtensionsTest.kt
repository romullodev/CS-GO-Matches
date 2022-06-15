package com.fuzy.csgomatches

import androidx.test.platform.app.InstrumentationRegistry
import com.fuzy.csgomatches.util.formatTimeFromIso8601
import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset


class GlobalExtensionsTest {

    @Test
    fun convert_time_based_on_local_timezone() {
        Instant.now(
            Clock.fixed(
            Instant.parse("2022-06-16T00:00:00Z"),
            ZoneOffset.UTC))

        val day = "14"
        val month = "06"
        val year = "2022"
        val hour = "03"
        val min = "10"
        assertEquals("Hoje, 20:59", "$year-$month-${day}T${hour}:${min}:00Z".formatTimeFromIso8601(
            InstrumentationRegistry.getInstrumentation().targetContext
        ))
    }
}