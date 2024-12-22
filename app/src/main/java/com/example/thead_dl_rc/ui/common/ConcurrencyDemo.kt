package com.example.thead_dl_rc.ui.common

import com.example.thead_dl_rc.ui.common.Lines.Line1
import com.example.thead_dl_rc.ui.common.Lines.Line2

object ConcurrencyDemo {
    val Manager1 = Any()
    val Manager2 = Any()
    val SortStation = Any()

    @Volatile
    var canCount = 0

    fun startDeadlock(logCallback: (String) -> Unit) {
        val line1 = Line1(logCallback, Scenario.DEADLOCK)
        val line2 = Line2(logCallback, Scenario.DEADLOCK)
        line1.start()
        line2.start()
    }

    fun startRaceCondition(logCallback: (String) -> Unit) {
        val line1 = Line1(logCallback, Scenario.RACE)
        val line2 = Line2(logCallback, Scenario.RACE)
        line1.start()
        line2.start()
    }

    fun startPriority(logCallback: (String) -> Unit) {
        val line1 = Line1(logCallback, Scenario.PRIORITY)
        val line2 = Line2(logCallback, Scenario.PRIORITY)

        line1.start()
        Thread.sleep(200)
        line2.start()
    }

    fun startVolatile(logCallback: (String) -> Unit) {
        val line1 = Line1(logCallback, Scenario.VOLATILE)
        val line2 = Line2(logCallback, Scenario.VOLATILE)
        line1.start()
        line2.start()
    }

}