package com.example.thead_dl_rc.ui.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ScenarioRadioButton(
    scenario: Scenario,
    selectedScenario: Scenario,
    onSelect: (Scenario) -> Unit,
    label: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        RadioButton(
            selected = (scenario == selectedScenario),
            onClick = { onSelect(scenario) }
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(label)
    }
}