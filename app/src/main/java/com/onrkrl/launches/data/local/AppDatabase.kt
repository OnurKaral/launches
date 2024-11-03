package com.onrkrl.launches.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.onrkrl.launches.data.local.dao.PositionDao
import com.onrkrl.launches.data.local.dao.SatelliteDetailDao
import com.onrkrl.launches.data.local.entity.PositionEntity
import com.onrkrl.launches.data.local.entity.SatelliteDetailEntity

@Database(
    entities = [SatelliteDetailEntity::class, PositionEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun satelliteDetailDao(): SatelliteDetailDao
    abstract fun positionDao(): PositionDao
}
