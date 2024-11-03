package com.onrkrl.launches.data.datasource


import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.onrkrl.launches.domain.model.Position
import com.onrkrl.launches.domain.model.Satellite
import com.onrkrl.launches.domain.model.SatelliteDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val context: Context
) : SatelliteDataSource {

    override suspend fun getSatellites(): List<Satellite> = withContext(Dispatchers.IO) {
        val jsonString =
            context.assets.open("satellites.json").bufferedReader().use { it.readText() }
        val type = object : TypeToken<List<Satellite>>() {}.type
        Gson().fromJson(jsonString, type)
    }

    override suspend fun getSatelliteDetail(id: Int): SatelliteDetail? =
        withContext(Dispatchers.IO) {
            val jsonString =
                context.assets.open("satellite-detail.json").bufferedReader().use { it.readText() }
            val type = object : TypeToken<List<SatelliteDetail>>() {}.type
            val details: List<SatelliteDetail> = Gson().fromJson(jsonString, type)
            details.find { it.id == id }
        }

    override suspend fun getPositions(id: Int): List<Position> = withContext(Dispatchers.IO) {
        val jsonString =
            context.assets.open("positions.json").bufferedReader().use { it.readText() }
        val jsonObject = JSONObject(jsonString)
        val listArray = jsonObject.getJSONArray("list")
        for (i in 0 until listArray.length()) {
            val item = listArray.getJSONObject(i)
            if (item.getString("id") == id.toString()) {
                val positionsArray = item.getJSONArray("positions")
                val positions = mutableListOf<Position>()
                for (j in 0 until positionsArray.length()) {
                    val pos = positionsArray.getJSONObject(j)
                    positions.add(Position(pos.getDouble("posX"), pos.getDouble("posY")))
                }
                return@withContext positions
            }
        }
        emptyList()
    }
}
