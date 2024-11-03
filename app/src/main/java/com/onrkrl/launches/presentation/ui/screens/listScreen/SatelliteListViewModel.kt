package com.onrkrl.launches.presentation.ui.screens.listScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onrkrl.launches.domain.model.Satellite
import com.onrkrl.launches.domain.repository.SatelliteRepository
import com.onrkrl.launches.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SatelliteListViewModel @Inject constructor(
    private val repository: SatelliteRepository
) : ViewModel() {

    private val _satellites = MutableStateFlow<Resource<List<Satellite>>>(Resource.Loading())
    val satellites: StateFlow<Resource<List<Satellite>>> = _satellites.asStateFlow()

    val searchQuery = MutableStateFlow("")

    init {
        fetchSatellites()
    }

    private fun fetchSatellites() {
        viewModelScope.launch {
            try {
                _satellites.value = Resource.Loading()
                delay(2000)

                val list = repository.getSatellites()
                _satellites.value = Resource.Success(list)
            } catch (e: Exception) {
                _satellites.value = Resource.Error(e.message ?: "An error occurred")
            }
        }
    }

    @OptIn(FlowPreview::class)
    val filteredSatellites = searchQuery
        .debounce(500)
        .combine(_satellites) { query, resource ->
            when (resource) {
                is Resource.Success -> {
                    val satellites = resource.data
                    if (query.isEmpty()) satellites
                    else satellites.filter { it.name.contains(query, ignoreCase = true) }
                }

                else -> emptyList()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}
