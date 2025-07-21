package com.example.composeroutemap.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.naver.maps.geometry.LatLng

object LocationStore{
    var current by mutableStateOf<LatLng?>(null)
}