package com.example.composeroutemap.ui.splash

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composeroutemap.data.LocationStore
import com.example.composeroutemap.utils.requestLocationPermissionsIfNeeded
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY
import com.naver.maps.geometry.LatLng
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class SplashViewModel(application: Application) : AndroidViewModel(application){

    private val fused = LocationServices.getFusedLocationProviderClient(application)

    var ready by mutableStateOf(false)

    fun fetchCurrentLocation() = viewModelScope.launch {
        // lastLocation 없으면 최신 위치 1회 요청
        val loc = fused.lastLocation.await()
            ?: fused.getCurrentLocation(PRIORITY_HIGH_ACCURACY, null).await()

        LocationStore.current = loc?.let { LatLng(it.latitude, it.longitude) }
        ready = true
    }

    // 권한 거부 등의 경우 바로 넘어 가는 함수.
    fun skip(){
        ready = true
    }
}