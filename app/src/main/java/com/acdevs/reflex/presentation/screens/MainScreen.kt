package com.acdevs.reflex.presentation.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.acdevs.reflex.presentation.navigations.NavigationItem

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val items = listOf(
        NavigationItem.Discover,
        NavigationItem.Favourites,
        NavigationItem.Profile
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination?.route

                items.forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.title) },
                        label = { Text(item.title) },
                        selected = currentDestination?.startsWith(item.title) == true,
                        onClick = {
                            navController.navigate(item.title) {

                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }

                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavigationItem.Discover.title,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(NavigationItem.Discover.title) { DiscoverScreen() }
            composable(NavigationItem.Favourites.title) { FavouritesScreen() }
            composable(NavigationItem.Profile.title) { ProfileScreen() }
        }
    }
}