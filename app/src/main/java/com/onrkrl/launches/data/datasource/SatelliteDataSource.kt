package com.onrkrl.launches.data.datasource

import com.onrkrl.launches.domain.model.Position
import com.onrkrl.launches.domain.model.Satellite
import com.onrkrl.launches.domain.model.SatelliteDetail

interface SatelliteDataSource {
    suspend fun getSatellites(): List<Satellite>
    suspend fun getSatelliteDetail(id: Int): SatelliteDetail?
    suspend fun getPositions(id: Int): List<Position>
}
