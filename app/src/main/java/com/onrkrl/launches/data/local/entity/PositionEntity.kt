package com.onrkrl.launches.data.local.entity

import androidx.room.Entity

@Entity(
    tableName = "positions",
    primaryKeys = ["satelliteId", "order"]
)
data class PositionEntity(
    val satelliteId: Int,
    val order: Int,
    val posX: Double,
    val posY: Double
)
