package com.example.respfitprototype.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route : String,
    val title: String,
    val icon: ImageVector
)
{
    object Home: BottomBarScreen(
        route = "home",
        title= "Home",
        icon = Icons.Rounded.Home
    )
    object Fitness: BottomBarScreen(
        route = "fitness",
        title= "Fitness",
        icon = Icons.Rounded.MonitorHeart
    )
    object Weight: BottomBarScreen(
        route = "weight",
        title= "Weight",
        icon = Icons.Rounded.MonitorWeight
    )

}

