package com.cruning.presentation.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

interface Destination {
    val route: String
}

object ListDestination : Destination {
    override val route = "list"
}

object DetailsDestination : Destination {
    override val route = "details"
    const val argsUrl = "url"
    val routeWithArgs = "$route/{$argsUrl}"
    val arguments = listOf(
        navArgument(name = argsUrl) { type = NavType.StringType },
    )
}