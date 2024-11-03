package com.onrkrl.launches.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.onrkrl.launches.data.local.entity.PositionEntity

@Dao
interface PositionDao {

    @Query("SELECT * FROM positions WHERE satelliteId = :satelliteId ORDER BY `order` ASC")
    suspend fun getPositions(satelliteId: Int): List<PositionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPositions(positions: List<PositionEntity>)
}
