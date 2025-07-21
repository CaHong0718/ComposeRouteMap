package com.example.composeroutemap.ui.map

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.composeroutemap.utils.hasLocationPermission
import com.google.android.gms.location.FusedLocationProviderClient
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.NaverMap
import com.naver.maps.map.util.FusedLocationSource

class NaverMapViewModel : ViewModel() {
    var naverMap: NaverMap? = null

    private fun moveToMyLocation(lat: Double, lng: Double) {
        naverMap?.moveCamera(CameraUpdate.scrollTo(LatLng(lat, lng)))
    }

    @SuppressLint("MissingPermission")
    fun moveToCurrentLocation(
        context: Context,
        fusedLocationClient: FusedLocationProviderClient,
    ) {
        if (hasLocationPermission(context)) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    moveToMyLocation(it.latitude, it.longitude)
                }
            }
        } else {
            Toast.makeText(context, "위치 권한이 필요합니다", Toast.LENGTH_SHORT).show()
        }
    }

    fun setupNaverMap(activity: Activity, context: Context, naverMap: NaverMap) {
        naverMap.uiSettings.isLocationButtonEnabled = false
        naverMap.uiSettings.isZoomControlEnabled = false


        if (hasLocationPermission(context)) {
            val locationSource = FusedLocationSource(activity, 1000)
            naverMap.locationSource = locationSource
            naverMap.locationTrackingMode = LocationTrackingMode.Follow
        }
    }
}