package com.onrkrl.launches.data.mapper

import com.onrkrl.launches.data.local.entity.PositionEntity
import com.onrkrl.launches.data.local.entity.SatelliteDetailEntity
import com.onrkrl.launches.domain.model.Position
import com.onrkrl.launches.domain.model.SatelliteDetail

fun SatelliteDetailEntity.toDomainModel() = SatelliteDetail(
    id = id,
    costPerLaunch = costPerLaunch,
    firstFlight = firstFlight,
    height = height,
    mass = mass
)

fun SatelliteDetail.toEntity() = SatelliteDetailEntity(
    id = id,
    costPerLaunch = costPerLaunch,
    firstFlight = firstFlight,
    height = height,
    mass = mass
)

fun PositionEntity.toDomainModel() = Position(
    posX = posX,
    posY = posY
)

fun Position.toEntity(satelliteId: Int, order: Int) = PositionEntity(
    satelliteId = satelliteId,
    order = order,
    posX = posX,
    posY = posY
)
