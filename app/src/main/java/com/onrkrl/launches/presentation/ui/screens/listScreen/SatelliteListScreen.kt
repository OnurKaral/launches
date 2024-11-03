package com.onrkrl.launches.presentation.ui.screens.listScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.onrkrl.launches.presentation.ui.screens.listScreen.widget.SatelliteListItem
import com.onrkrl.launches.util.Resource

@Composable
fun SatelliteListScreen(navController: NavController) {
    val viewModel: SatelliteListViewModel = hiltViewModel()
    val satellitesResource by viewModel.satellites.collectAsState()
    val satellites = viewModel.filteredSatellites.collectAsState()
    val query by viewModel.searchQuery.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            value = query,
            onValueChange = { viewModel.searchQuery.value = it },
            placeholder = { Text("Search by name") },
            leadingIcon = {
                Icon(Icons.Filled.Search, contentDescription = "Search Icon")
            },
            singleLine = true,
            shape = MaterialTheme.shapes.small.copy(CornerSize(8.dp)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        when (satellitesResource) {
            is Resource.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is Resource.Success -> {
                LazyColumn {
                    items(satellites.value) { satellite ->
                        SatelliteListItem(satellite = satellite, onClick = {
                            navController.navigate("detail/${satellite.id}")
                        })
                        HorizontalDivider()
                    }
                }
            }
            is Resource.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = (satellitesResource as Resource.Error).message)
                }
            }
        }
    }
}
