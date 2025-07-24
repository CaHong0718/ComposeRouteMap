package com.example.composeroutemap.ui.map

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.composeroutemap.R
import com.example.composeroutemap.data.Dimens
import com.example.composeroutemap.data.LocationStore
import com.example.composeroutemap.data.MapMarkers
import com.example.composeroutemap.utils.checkLocationPermissionAnd
import com.example.composeroutemap.utils.hasLocationPermission
import com.google.android.gms.location.FusedLocationProviderClient
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.NaverMap
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.overlay.PathOverlay
import com.naver.maps.map.util.FusedLocationSource

class NaverMapViewModel : ViewModel() {
    var naverMap: NaverMap? = null
    val placeMarkers = mutableListOf<Marker>()
    var currentOverlay: PathOverlay = PathOverlay().apply {
        color            = android.graphics.Color.GRAY
        width            = 20
        patternImage     = OverlayImage.fromResource(R.drawable.arrow_tile)
        patternInterval  = 80
    }

    private fun moveToMyLocation(lat: Double, lng: Double) {
        val update = CameraUpdate.scrollAndZoomTo(LatLng(lat, lng), Dimens.DefaultCameraZoom)

        naverMap?.moveCamera(update)
    }

    fun moveToCurrentLocation(context: Context, ) {
        checkLocationPermissionAnd(context, function = {
            LocationStore.current?.let { moveToMyLocation(it.latitude, it.longitude) }
        })
    }

    fun setupNaverMap(context: Context, naverMap: NaverMap) {
        naverMap.uiSettings.isLocationButtonEnabled = false
        naverMap.uiSettings.isZoomControlEnabled = false
        naverMap.uiSettings.isCompassEnabled = false

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