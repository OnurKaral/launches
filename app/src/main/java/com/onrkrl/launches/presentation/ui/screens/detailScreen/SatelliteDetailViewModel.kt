package com.onrkrl.launches.presentation.ui.screens.detailScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onrkrl.launches.domain.model.Position
import com.onrkrl.launches.domain.model.SatelliteDetail
import com.onrkrl.launches.domain.repository.SatelliteRepository
import com.onrkrl.launches.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SatelliteDetailViewModel @Inject constructor(
    private val repository: SatelliteRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val satelliteId: Int = savedStateHandle["id"] ?: 0

    private val _satelliteDetail = MutableStateFlow<Resource<SatelliteDetail>>(Resource.Loading())
    val satelliteDetail: StateFlow<Resource<SatelliteDetail>> = _satelliteDetail.asStateFlow()

    private val _positions = MutableStateFlow<Resource<List<Position>>>(Resource.Loading())

    private val _currentPosition = MutableStateFlow<Position?>(null)
    val currentPosition: StateFlow<Position?> = _currentPosition.asStateFlow()

    init {
        fetchSatelliteDetail()
        fetchPositions()
    }

    private fun fetchSatelliteDetail() {
        viewModelScope.launch {
            try {
                val detail = repository.getSatelliteDetail(satelliteId)
                if (detail != null) {
                    _satelliteDetail.value = Resource.Success(detail)
                } else {
                    _satelliteDetail.value = Resource.Error("Satellite detail not found")
                }
            } catch (e: Exception) {
                _satelliteDetail.value = Resource.Error(e.message ?: "An error occurred")
            }
        }
    }

    private fun fetchPositions() {
        viewModelScope.launch {
            try {
                val positionsList = repository.getPositions(satelliteId)
                if (positionsList.isNotEmpty()) {
                    _positions.value = Resource.Success(positionsList)
                    emitPositions(positionsList)
                } else {
                    _positions.value = Resource.Error("Positions not found")
                }
            } catch (e: Exception) {
                _positions.value = Resource.Error(e.message ?: "An error occurred")
            }
        }
    }

    private fun emitPositions(positions: List<Position>) {
        viewModelScope.launch {
            while (true) {
                for (position in positions) {
                    _currentPosition.value = position
                    delay(3000)
                }
            }
        }
    }
}
