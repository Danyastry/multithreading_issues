package com.example.thead_dl_rc.ui.common.Lines

import com.example.thead_dl_rc.ui.common.Scenario
import com.example.thead_dl_rc.ui.common.ConcurrencyDemo.Manager1
import com.example.thead_dl_rc.ui.common.ConcurrencyDemo.Manager2
import com.example.thead_dl_rc.ui.common.ConcurrencyDemo.SortStation
import com.example.thead_dl_rc.ui.common.ConcurrencyDemo.canCount

class Line2(
    private val logCallback: (String) -> Unit,
    private val scenario: Scenario
) : Thread() {

    override fun run() {
        when (scenario) {
            Scenario.VOLATILE -> runVolatileScenario()
            Scenario.DEADLOCK -> runDeadlockScenario()
            Scenario.RACE -> runRaceScenario()
            Scenario.PRIORITY -> runPriorityScenario()
        }
    }

    private fun runVolatileScenario() {
        var localCount = canCount
        while (canCount < 6) {
            canCount = ++localCount
            logCallback("Line2: Incrementing canCount to $canCount")
            sleep(100)
        }
    }

    private fun runDeadlockScenario() {
        synchronized(Manager2) {
            logCallback("Line2: Holding Manager2...")
            sleep(200)
            logCallback("Line2: Waiting for Manager1...")
            synchronized(Manager1) {
                logCallback("Line2: Holding Manager1 and Manager2")
            }
        }
    }

    private fun runRaceScenario() {
        logCallback("Line2: Starting to create Product2 cans")
        sleep(2000)
        logCallback("Line2: Adding Product2 cans to SortStation")

        for (i in 0 until 20) {
            if (canCount >= 20) break
            canCount++
            sleep(1000)
            logCallback("Line2: ${i + 1} can(s) of Product2 added, total cans = $canCount")
        }
    }

    private fun runPriorityScenario() {
        logCallback("Line2: 20 cans of Product2 delivered to SortStation")
        synchronized(SortStation) {
            logCallback("Line2: Locked SortStation")
            for (i in 0 until 20) {
                logCallback("Line2: Packing can #${i + 1} of Product2...")
                sleep(100)
            }
            logCallback("Line2: Product2 packing finished")
        }
    }
}