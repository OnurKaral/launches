package com.onrkrl.launches.domain.model

data class SatelliteDetail(
    val id: Int,
    val costPerLaunch: Long,
    val firstFlight: String,
    val height: Int,
    val mass: Int
)