package com.example.thead_dl_rc.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.thead_dl_rc.ui.common.ConcurrencyDemo
import com.example.thead_dl_rc.ui.common.Scenario
import com.example.thead_dl_rc.ui.common.ScenarioRadioButton


@Composable
fun ThreadDemoApp() {
    val logs = remember { mutableStateListOf<String>() }
    var selectedScenario by remember { mutableStateOf(Scenario.DEADLOCK) }

    val addLine: (String) -> Unit = { text ->
        appendLogSafely(logs, text)
    }

    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.padding(top = 40.dp, start = 12.dp)) {
                Text(text = "Demonstration of Multithreading Issues",
                    style = MaterialTheme.typography.titleLarge)

                Spacer(modifier = Modifier.height(8.dp))

                ScenarioRadioButton(
                    scenario = Scenario.DEADLOCK,
                    selectedScenario = selectedScenario,
                    onSelect = { selectedScenario = it },
                    label = "DeadLock"
                )
                ScenarioRadioButton(
                    scenario = Scenario.RACE,
                    selectedScenario = selectedScenario,
                    onSelect = { selectedScenario = it },
                    label = "Race Condition"
                )
                ScenarioRadioButton(
                    scenario = Scenario.PRIORITY,
                    selectedScenario = selectedScenario,
                    onSelect = { selectedScenario = it },
                    label = "Priority Inversion"
                )
                ScenarioRadioButton(
                    scenario = Scenario.VOLATILE,
                    selectedScenario = selectedScenario,
                    onSelect = { selectedScenario = it },
                    label = "Volatile"
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        logs.clear()
                        ConcurrencyDemo.canCount = 0
                        when (selectedScenario) {
                            Scenario.DEADLOCK -> ConcurrencyDemo.startDeadlock(addLine)
                            Scenario.RACE -> ConcurrencyDemo.startRaceCondition(addLine)
                            Scenario.PRIORITY -> ConcurrencyDemo.startPriority(addLine)
                            Scenario.VOLATILE -> ConcurrencyDemo.startVolatile(addLine)
                        }
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Start")
                }

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(logs) { line ->
                        Text(text = line)
                    }
                }
            }
        }
    }
}

private fun appendLogSafely(logs: MutableList<String>, message: String) {
    logs.add(message)
}
