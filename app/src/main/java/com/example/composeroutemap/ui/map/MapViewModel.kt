package com.example.composeroutemap.ui.map

import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationServices
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.NaverMap
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MapViewModel : ViewModel() {
    var naverMap: NaverMap? = null

    fun moveToMyLocation(lat: Double, lng: Double) {
        naverMap?.moveCamera(CameraUpdate.scrollTo(LatLng(lat, lng)))
    }
}