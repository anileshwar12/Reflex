package com.acdevs.reflex.presentation.navigations

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItem(val title: String, val icon: ImageVector) {
    object Discover : NavigationItem("Discover", Icons.Default.Home)
    object Favourites : NavigationItem("Favourites", Icons.Default.Favorite)
    object Profile : NavigationItem("Profile", Icons.Default.Person)
}
