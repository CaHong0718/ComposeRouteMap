package com.example.composeroutemap.ui.map

import android.app.Activity
import android.content.Context
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
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


@Composable
fun NaverMapScreen(viewModel: NaverMapViewModel) {
    val context = LocalContext.current
    val activity = context as Activity
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val mapView = rememberMapViewWithLifecycle()

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(factory = { mapView }) { view ->
            view.getMapAsync { naverMap ->
                viewModel.naverMap = naverMap
                viewModel.setupNaverMap(activity, context, naverMap)
            }
        }

        MyLocationButton(
            modifier = Modifier.align(Alignment.BottomStart),
            onClick = { onClickMyLocationButton(viewModel, context, fusedLocationClient) }
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

private fun onClickMyLocationButton(viewModel: NaverMapViewModel, context: Context, fusedLocationClient: FusedLocationProviderClient){
    viewModel.moveToCurrentLocation(context, fusedLocationClient)
}



