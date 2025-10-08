package com.example.barcodescanapp.ui.screens.configuration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.barcodescanapp.ui.topbar.TopBarYou

@Preview
@Composable
fun ConfigurationScreen() {

    var isDarkMode = remember { mutableStateOf(true) }

    Scaffold (
        topBar = { TopBarYou(nav = {}, title = "Configurations") }
    ){ paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(vertical = 10.dp, horizontal = 20.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("Dark mode?")
                    Spacer(modifier = Modifier.weight(1f))
                    Switch(
                        checked = isDarkMode.value,
                        onCheckedChange = {
                            isDarkMode.value = !isDarkMode.value
                        }
                    )
                }
            }
        }

    }

}