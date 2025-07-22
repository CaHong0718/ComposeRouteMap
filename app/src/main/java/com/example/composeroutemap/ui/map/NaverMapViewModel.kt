package com.example.composeroutemap.ui.map

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.composeroutemap.data.LocationStore
import com.example.composeroutemap.data.MapMarkers
import com.example.composeroutemap.utils.checkLocationPermissionAnd
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

    fun moveToCurrentLocation(context: Context, ) {
        checkLocationPermissionAnd(context, function = {
            LocationStore.current?.let { moveToMyLocation(it.latitude, it.longitude) }
        })
    }

    fun setupNaverMap(context: Context, naverMap: NaverMap) {
        naverMap.uiSettings.isLocationButtonEnabled = false
        naverMap.uiSettings.isZoomControlEnabled = false

        checkLocationPermissionAnd(context, function = {
            // Tracking 비활성화
            /*val locationSource = FusedLocationSource(activity, 1000)
            naverMap.locationSource = locationSource
            naverMap.locationTrackingMode = LocationTrackingMode.Follow*/
            LocationStore.current?.let { moveToMyLocation(it.latitude, it.longitude) }

            MapMarkers.myLocationMarker.apply {
                position = LocationStore.current?.let { LatLng(it.latitude, it.longitude) }!!
                map = naverMap
            }
        })
    }
}