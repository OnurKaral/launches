package com.onrkrl.launches.data.mapper

import com.onrkrl.launches.data.local.entity.PositionEntity
import com.onrkrl.launches.data.local.entity.SatelliteDetailEntity
import com.onrkrl.launches.domain.model.Position
import com.onrkrl.launches.domain.model.SatelliteDetail
import org.junit.Assert.assertEquals
import org.junit.Test

class EntityMapperTest {

    @Test
    fun `test SatelliteDetailEntity to SatelliteDetail mapping`() {
        val satelliteDetailEntity = SatelliteDetailEntity(
            id = 1,
            costPerLaunch = 5000000,
            firstFlight = "2022-01-01",
            height = 50,
            mass = 1000
        )

        val satelliteDetail = satelliteDetailEntity.toDomainModel()

        assertEquals(satelliteDetailEntity.id, satelliteDetail.id)
        assertEquals(satelliteDetailEntity.costPerLaunch, satelliteDetail.cost_per_launch)
        assertEquals(satelliteDetailEntity.firstFlight, satelliteDetail.first_flight)
        assertEquals(satelliteDetailEntity.height, satelliteDetail.height)
        assertEquals(satelliteDetailEntity.mass, satelliteDetail.mass)
    }

    @Test
    fun `test SatelliteDetail to SatelliteDetailEntity mapping`() {
        val satelliteDetail = SatelliteDetail(
            id = 1,
            cost_per_launch = 5000000,
            first_flight = "2022-01-01",
            height = 50,
            mass = 1000
        )

        val satelliteDetailEntity = satelliteDetail.toEntity()

        assertEquals(satelliteDetail.id, satelliteDetailEntity.id)
        assertEquals(satelliteDetail.cost_per_launch, satelliteDetailEntity.costPerLaunch)
        assertEquals(satelliteDetail.first_flight, satelliteDetailEntity.firstFlight)
        assertEquals(satelliteDetail.height, satelliteDetailEntity.height)
        assertEquals(satelliteDetail.mass, satelliteDetailEntity.mass)
    }

    @Test
    fun `test PositionEntity to Position mapping`() {
        val positionEntity = PositionEntity(
            satelliteId = 1,
            order = 1,
            posX = 100.0,
            posY = 200.0
        )

        val position = positionEntity.toDomainModel()

        assertEquals(positionEntity.posX, position.posX, 0.0)
        assertEquals(positionEntity.posY, position.posY, 0.0)
    }

    @Test
    fun `test Position to PositionEntity mapping`() {
        val position = Position(
            posX = 100.0,
            posY = 200.0
        )
        val satelliteId = 1
        val order = 1

        val positionEntity = position.toEntity(satelliteId, order)

        assertEquals(satelliteId, positionEntity.satelliteId)
        assertEquals(order, positionEntity.order)
        assertEquals(position.posX, positionEntity.posX, 0.0)
        assertEquals(position.posY, positionEntity.posY, 0.0)
    }
}
