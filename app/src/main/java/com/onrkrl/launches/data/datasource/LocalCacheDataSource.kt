package com.onrkrl.launches.data.datasource

import com.onrkrl.launches.data.local.AppDatabase
import com.onrkrl.launches.data.mapper.toDomainModel
import com.onrkrl.launches.data.mapper.toEntity
import com.onrkrl.launches.domain.model.Position
import com.onrkrl.launches.domain.model.SatelliteDetail
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class LocalCacheDataSource @Inject constructor(
    private val database: AppDatabase
) {
    suspend fun getSatelliteDetail(id: Int): SatelliteDetail? = withContext(Dispatchers.IO) {
        database.satelliteDetailDao().getSatelliteDetail(id)?.toDomainModel()
    }

    suspend fun saveSatelliteDetail(detail: SatelliteDetail) = withContext(Dispatchers.IO) {
        database.satelliteDetailDao().insertSatelliteDetail(detail.toEntity())
    }

    suspend fun getPositions(id: Int): List<Position> = withContext(Dispatchers.IO) {
        database.positionDao().getPositions(id).map { it.toDomainModel() }
    }

    suspend fun savePositions(id: Int, positions: List<Position>) = withContext(Dispatchers.IO) {
        val entities = positions.mapIndexed { index, position ->
            position.toEntity(satelliteId = id, order = index)
        }
        database.positionDao().insertPositions(entities)
    }
}
