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

    private val gson = Gson()
    private val satellitesData by lazy { loadJsonFromAssets("satellites.json") }
    private val satelliteDetailsData by lazy { loadJsonFromAssets("satellite-detail.json") }
    private val positionsData by lazy { loadJsonFromAssets("positions.json") }

    private fun loadJsonFromAssets(fileName: String): String =
        context.assets.open(fileName).bufferedReader().use { it.readText() }

    override suspend fun getSatellites(): List<Satellite> = withContext(Dispatchers.IO) {
        val type = object : TypeToken<List<Satellite>>() {}.type
        gson.fromJson(satellitesData, type)
    }

    override suspend fun getSatelliteDetail(id: Int): SatelliteDetail? = withContext(Dispatchers.IO) {
        val type = object : TypeToken<List<SatelliteDetail>>() {}.type
        val details: List<SatelliteDetail> = gson.fromJson(satelliteDetailsData, type)
        details.find { it.id == id }
    }

    override suspend fun getPositions(id: Int): List<Position> = withContext(Dispatchers.IO) {
        val jsonObject = JSONObject(positionsData)
        val listArray = jsonObject.getJSONArray("list")
        for (i in 0 until listArray.length()) {
            val item = listArray.getJSONObject(i)
            if (item.getString("id") == id.toString()) {
                val positionsArray = item.getJSONArray("positions")
                return@withContext List(positionsArray.length()) { j ->
                    val pos = positionsArray.getJSONObject(j)
                    Position(pos.getDouble("posX"), pos.getDouble("posY"))
                }
            }
        }
        emptyList()
    }
}

