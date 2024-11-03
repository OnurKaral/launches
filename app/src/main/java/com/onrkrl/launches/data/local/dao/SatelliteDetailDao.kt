package com.onrkrl.launches.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.onrkrl.launches.data.local.entity.SatelliteDetailEntity

@Dao
interface SatelliteDetailDao {

    @Query("SELECT * FROM satellite_details WHERE id = :id")
    suspend fun getSatelliteDetail(id: Int): SatelliteDetailEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSatelliteDetail(detail: SatelliteDetailEntity)
}
