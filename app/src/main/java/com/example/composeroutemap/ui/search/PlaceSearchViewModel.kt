package com.example.composeroutemap.ui.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class PlaceSearchViewModel : ViewModel(){
    var query by mutableStateOf("")
        private set

    fun onQueryChange(newValue: String){
        query = newValue
    }
}