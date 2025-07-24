package com.example.composeroutemap.ui.search

import androidx.lifecycle.ViewModel
import com.example.composeroutemap.data.Place
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class PlaceUiModel(
    val name: String,
    val address: String,
)

class SearchViewModel : ViewModel() {

    private val _selected = MutableStateFlow<List<PlaceUiModel>>(emptyList())
    val selected: StateFlow<List<PlaceUiModel>> get() = _selected

    fun addPlace(place: Place) = _selected.update { list ->
        val ui = place.toUiModel()
        if (list.any { it.name == ui.name && it.address == ui.address }) list
        else list + ui
    }

    private fun Place.toUiModel() = PlaceUiModel(
        name = name,
        address = roadAddress.ifBlank { category }
    )

    fun removePlace(place: PlaceUiModel) = _selected.update { it - place }
}