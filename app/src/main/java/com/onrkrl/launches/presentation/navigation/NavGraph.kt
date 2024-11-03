package com.onrkrl.launches.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.onrkrl.launches.presentation.ui.screens.listScreen.SatelliteListScreen

@Composable
fun NavGraph(startDestination: String = "list") {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination) {
        composable("list") {
            SatelliteListScreen(navController)
        }
    }
}