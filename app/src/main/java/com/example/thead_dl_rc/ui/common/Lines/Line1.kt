package com.example.thead_dl_rc.ui.common.Lines

import com.example.thead_dl_rc.ui.common.ConcurrencyDemo.Manager1
import com.example.thead_dl_rc.ui.common.ConcurrencyDemo.Manager2
import com.example.thead_dl_rc.ui.common.ConcurrencyDemo.SortStation
import com.example.thead_dl_rc.ui.common.ConcurrencyDemo.canCount
import com.example.thead_dl_rc.ui.common.Scenario

class Line1(
    private val logCallback: (String) -> Unit,
    private val scenario: Scenario
) : Thread() {

    override fun run() {
        when (scenario) {
            Scenario.VOLATILE -> runVolatile()
            Scenario.DEADLOCK -> runDeadLock()
            Scenario.RACE -> runRaceCondition()
            Scenario.PRIORITY -> runPriorityInversion()
        }
    }

    private fun runVolatile() {
        var localCount = canCount
        while (localCount < 6) {
            if (localCount != canCount) {
                localCount = canCount
                logCallback("Line1: Updated canCount = $localCount")
            }
        }
        logCallback("Line1: Loop finished. localCount=$localCount, canCount=$canCount")
    }

    private fun runDeadLock() {
        synchronized(Manager1) {
            logCallback("Line1: Holding Manager1...")
            sleep(200)
            logCallback("Line1: Waiting for Manager2...")
            synchronized(Manager2) {
                logCallback("Line1: Holding Manager1 and Manager2")
            }
        }
    }

    private fun runRaceCondition() {
        logCallback("Line1: Starting to create Product1 cans")
        sleep(3000)
        logCallback("Line1: Adding Product1 cans to SortStation")

        for (i in 0 until 20) {
            if (canCount >= 20) break
            canCount++
            sleep(1000)
            logCallback("Line1: ${i + 1} can(s) of Product1 added, total cans = $canCount")
        }
    }

    private fun runPriorityInversion() {
        logCallback("Line1: 20 cans of Product1 delivered to SortStation")
        synchronized(SortStation) {
            logCallback("Line1: Locked SortStation")
            for (i in 0 until 20) {
                logCallback("Line1: Packing can #${i + 1} of Product1...")
                sleep(100)
            }
            logCallback("Line1: Product1 packing finished")
        }
    }
}