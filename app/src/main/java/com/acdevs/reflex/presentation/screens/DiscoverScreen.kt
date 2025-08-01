package com.acdevs.reflex.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.acdevs.reflex.presentation.components.AutomationCard
import com.acdevs.reflex.presentation.ui.theme.CardBlue
import com.acdevs.reflex.presentation.ui.theme.CardGreen
import com.acdevs.reflex.presentation.ui.theme.CardLavender
import com.acdevs.reflex.presentation.ui.theme.CardPeach
import com.acdevs.reflex.presentation.ui.theme.CardPink

@Composable
fun DiscoverScreen() {
    val sampleFunctions = listOf(
        Triple("Silent on SMS", Icons.Default.Notifications, CardLavender),
        Triple("Send Location", Icons.Default.LocationOn, CardGreen),
        Triple("Toggle WiFi", Icons.Default.Email, CardBlue),
        Triple("Vibrate on Charger Plug", Icons.Default.Call, CardPeach),
        Triple("Low Battery Alert", Icons.Default.AddCircle, CardPink)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 16.dp)
    ) {
        Text(
            text = "Discover Functions",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(start = 16.dp, bottom = 12.dp),
            color = MaterialTheme.colorScheme.onBackground
        )

        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(sampleFunctions.shuffled()) { (title, icon, bgColor) ->
                AutomationCard(
                    category = "call",
                    title = title,
                    icon = icon,
                    usersUsed = (100..500).random(),
                    isFavourite = false,
                    onFavouriteClick = { /* handle */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                )
            }
        }

    }
}

@Composable
@Preview
fun DiscoverScreenPreview() {
    DiscoverScreen()
}
