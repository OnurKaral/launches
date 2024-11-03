package com.onrkrl.launches.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.onrkrl.launches.presentation.navigation.NavGraph
import com.onrkrl.launches.ui.theme.LaunchesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LaunchesTheme {
                NavGraph()
            }
        }
    }
}