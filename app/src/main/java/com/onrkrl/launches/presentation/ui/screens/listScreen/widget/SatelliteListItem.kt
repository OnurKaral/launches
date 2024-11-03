package com.onrkrl.launches.presentation.ui.screens.listScreen.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.onrkrl.launches.domain.model.Satellite

@Composable
fun SatelliteListItem(satellite: Satellite, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .clip(CircleShape)
                .background(if (satellite.active) Color.Green else Color.Red)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = satellite.name,
            fontWeight = if (satellite.active) FontWeight.Bold else FontWeight.Light,
            modifier = Modifier.weight(1f)
        )
    }
}
