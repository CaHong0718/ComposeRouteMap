package com.example.composeroutemap.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeroutemap.data.LocationStore.current
import com.example.composeroutemap.data.Place
import com.example.composeroutemap.data.repository.RouteRepository
import com.example.composeroutemap.utils.ServiceLocator
import com.naver.maps.geometry.LatLng
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

data class PlaceUiModel(
    val id: String,
    val name: String,
    val address: String,
)

class SearchViewModel(private val repo: RouteRepository = ServiceLocator.routeRepository) : ViewModel() {

    private val _places = MutableStateFlow<List<Place>>(emptyList())
    val places: StateFlow<List<Place>> get() = _places.asStateFlow()

    val ui: StateFlow<List<PlaceUiModel>> =
        _places
            .map { list -> list.map { it.toUiModel() } }
            .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _polyline = MutableStateFlow<List<LatLng>>(emptyList())  // NEW
    val polyline = _polyline.asStateFlow()

    fun addPlace(place: Place) = _places.update { list ->
        if (list.any { it.name == place.name && it.roadAddress == place.roadAddress }) list
        else list + place
    }

    private fun Place.toUiModel() = PlaceUiModel(
        id = id.ifBlank { UUID.randomUUID().toString() },
        name = name,
        address = roadAddress.ifBlank { category }
    )

    fun removePlace(ui: PlaceUiModel) = _places.update { list ->
        list.filterNot { it. id == ui.id }
    }

    fun calculateOptimalRoute() = viewModelScope.launch {
        if (places.value.isEmpty()) return@launch
        _loading.value = true
        try {
            val startPlace = Place(
                id = "ME",
                name = "현재 위치",
                lat = current?.latitude,
                lng = current?.longitude,
                category = "",
                roadAddress = "",
            )
            val all = listOf(startPlace) + places.value

            _polyline.value = repo.calcRoutePolyline(all)
        } finally { _loading.value = false }
    }
}