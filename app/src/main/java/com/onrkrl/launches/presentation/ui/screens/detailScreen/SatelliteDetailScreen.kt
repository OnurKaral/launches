package com.onrkrl.launches.presentation.ui.screens.detailScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.onrkrl.launches.util.Resource

@Composable
fun SatelliteDetailScreen(satelliteId: Int, satelliteName: String) {
    val viewModel: SatelliteDetailViewModel = hiltViewModel()
    val satelliteDetailResource by viewModel.satelliteDetail.collectAsState()
    val currentPosition by viewModel.currentPosition.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (satelliteDetailResource) {
            is Resource.Loading -> {
                CircularProgressIndicator()
            }

            is Resource.Success -> {
                val detail = (satelliteDetailResource as Resource.Success).data
                Text(
                    text = satelliteName,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = detail.first_flight,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(64.dp))

                DetailText(title = "Height/Mass:", value = "${detail.height}/${detail.mass}")
                Spacer(modifier = Modifier.height(16.dp))
                DetailText(title = "Cost:", value = "${detail.cost_per_launch}")
                Spacer(modifier = Modifier.height(16.dp))
                DetailText(
                    title = "Last Position:",
                    value = "(${currentPosition?.posX}, ${currentPosition?.posY})"
                )

            }

            is Resource.Error -> {
                Text(text = (satelliteDetailResource as Resource.Error).message)
            }
        }
    }
}

@Composable
fun DetailText(title: String, value: String) {
    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append(title)
            }
            append(" $value")
        },
        style = MaterialTheme.typography.bodyMedium
    )
}