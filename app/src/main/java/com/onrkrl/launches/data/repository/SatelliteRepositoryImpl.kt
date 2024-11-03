package com.onrkrl.launches.data.repository

import com.onrkrl.launches.data.datasource.LocalCacheDataSource
import com.onrkrl.launches.data.datasource.LocalDataSource
import com.onrkrl.launches.domain.model.Position
import com.onrkrl.launches.domain.model.Satellite
import com.onrkrl.launches.domain.model.SatelliteDetail
import com.onrkrl.launches.domain.repository.SatelliteRepository
import javax.inject.Inject

class SatelliteRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val cacheDataSource: LocalCacheDataSource
) : SatelliteRepository {

    override suspend fun getSatellites(): List<Satellite> {
        return localDataSource.getSatellites()
    }

    override suspend fun getSatelliteDetail(id: Int): SatelliteDetail? {
        val cachedDetail = cacheDataSource.getSatelliteDetail(id)
        if (cachedDetail != null) {
            return cachedDetail
        }

        val detail = localDataSource.getSatelliteDetail(id)
        detail?.let { cacheDataSource.saveSatelliteDetail(it) }
        return detail
    }

    override suspend fun getPositions(id: Int): List<Position> {
        val cachedPositions = cacheDataSource.getPositions(id)
        if (cachedPositions.isNotEmpty()) {
            return cachedPositions
        }

        val positions = localDataSource.getPositions(id)
        if (positions.isNotEmpty()) {
            cacheDataSource.savePositions(id, positions)
        }
        return positions
    }
}
