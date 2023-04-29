package com.cruning.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cruning.presentation.screens.detail.DetailScreen
import com.cruning.presentation.screens.list.ListScreen
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun SmartwayNavHost(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = ListDestination.route,
    ) {
        composable(
            route = ListDestination.route,
        ) {
            ListScreen(
                click = { url ->
                    val encodedUrl = URLEncoder.encode(url, StandardCharsets.UTF_8.toString())
                    navController.navigateSingleTopTo("${DetailsDestination.route}/$encodedUrl")
                }
            )
        }
        composable(
            route = DetailsDestination.routeWithArgs,
            arguments = DetailsDestination.arguments,
        ) { backStackEntry ->
            val encodeUrl = backStackEntry.arguments?.getString(DetailsDestination.argsUrl, "")
            val url = URLDecoder.decode(encodeUrl, StandardCharsets.UTF_8.toString())
            DetailScreen(
                url = url,
                click = {
                    navController.popBackStack()
                }
            )
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }