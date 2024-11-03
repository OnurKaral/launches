package com.onrkrl.launches.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.onrkrl.launches.presentation.ui.screens.detailScreen.SatelliteDetailScreen
import com.onrkrl.launches.presentation.ui.screens.listScreen.SatelliteListScreen

@Composable
fun NavGraph(startDestination: String = "list") {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination) {
        composable("list") {
            SatelliteListScreen(navController)
        }

        composable(
            route = "detail/{id}/{name}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType },
                navArgument("name") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            val name = backStackEntry.arguments?.getString("name") ?: ""
            SatelliteDetailScreen(satelliteId = id, satelliteName = name)
        }
    }
}