package com.example.composeroutemap.ui.map

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composeroutemap.utils.hasLocationPermission
import com.example.composeroutemap.utils.requestLocationPermissionsIfNeeded
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource


@Composable
fun NaverMapScreen(viewModel: MapViewModel) {
    val context = LocalContext.current
    val activity = context as Activity
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val mapView = rememberMapViewWithLifecycle()

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(factory = { mapView }) { view ->
            view.getMapAsync { naverMap ->
                naverMap.uiSettings.isZoomControlEnabled = false
                viewModel.naverMap = naverMap
                setupNaverMap(activity, context, naverMap)
            }
        }

        MyLocationButton(
            modifier = Modifier.align(Alignment.BottomEnd),
            onClick = {
                moveToCurrentLocation(context, fusedLocationClient, viewModel)
            }
        )
    }
}

@Composable
fun MyLocationButton(modifier: Modifier,onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier.padding(16.dp)
    ) {
        Icon(imageVector = Icons.Default.LocationOn, contentDescription = "내 위치")
    }
}

fun setupNaverMap(activity: Activity, context: Context, naverMap: NaverMap) {
    naverMap.uiSettings.isLocationButtonEnabled = false

    if (hasLocationPermission(context)) {
        val locationSource = FusedLocationSource(activity, 1000)
        naverMap.locationSource = locationSource
        naverMap.locationTrackingMode = LocationTrackingMode.Follow
    }
}

fun moveToCurrentLocation(
    context: Context,
    fusedLocationClient: FusedLocationProviderClient,
    viewModel: MapViewModel
) {
    if (hasLocationPermission(context)) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestLocationPermissionsIfNeeded(context as Activity)

            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            location?.let {
                viewModel.moveToMyLocation(it.latitude, it.longitude)
            }
        }
    } else {
        Toast.makeText(context, "위치 권한이 필요합니다", Toast.LENGTH_SHORT).show()
    }
}