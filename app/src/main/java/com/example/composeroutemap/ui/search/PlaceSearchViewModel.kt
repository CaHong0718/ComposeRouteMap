package com.example.composeroutemap.ui.search

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeroutemap.data.LocationStore
import com.example.composeroutemap.data.Place
import com.example.composeroutemap.data.repository.PlaceRepository
import com.example.composeroutemap.utils.hasLocationPermission
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.http.Query

class PlaceSearchViewModel(app: Application) : AndroidViewModel(app) {

    private val repository = PlaceRepository(app)

    data class UiState(
        val query: String = "",
        val items: List<Place> = emptyList(),
        val isLoading: Boolean = false,
        val error: String? = null
    )

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    private var searchJob: Job? = null

    private fun List<Place>.withDistanceIfPermitted(): List<Place> {
        if (!hasLocationPermission(getApplication())) return this

        return map { place ->
            val dist = if (place.lat != null && place.lng != null)
                LocationStore.distanceTo(place.lat, place.lng)
            else null
            place.copy(distanceMeter = dist)
        }.sortedBy { it.distanceMeter ?: Int.MAX_VALUE } // 가까운 순
    }


    fun onQueryChange(newValue: String) {
        _state.update { it.copy(query = newValue) }

        searchJob?.cancel()
        if (newValue.isBlank()) {
            _state.update { it.copy(items = emptyList(), error = null) }
            return
        }

        searchJob = viewModelScope.launch {
            delay(400)
            _state.update { it.copy(isLoading = true) }

            repository.search(newValue)
                .onSuccess { list ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            items = list.withDistanceIfPermitted()
                        )
                    }
                }
                .onFailure { e ->
                    _state.update { it.copy(isLoading = false, error = e.message) }
                }
        }
    }
}