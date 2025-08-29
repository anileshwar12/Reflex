package com.acdevs.reflex.presentation.navigations

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItem(val route: String, val title: String, val icon: ImageVector) {
    object Discover : NavigationItem("discover", "Discover", Icons.Default.Home)
    object Favourites : NavigationItem("favourites", "Favourites", Icons.Default.Favorite)
    object Profile : NavigationItem("profile", "Profile", Icons.Default.Person)
}
