package com.example.composeroutemap.ui.map

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.example.composeroutemap.R
import com.example.composeroutemap.data.Dimens
import com.example.composeroutemap.data.Weights
import com.example.composeroutemap.ui.customwidget.RouteMapIcon
import com.example.composeroutemap.ui.customwidget.StatusBarIconColor
import com.example.composeroutemap.ui.customwidget.StatusBarScrim
import com.example.composeroutemap.ui.theme.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


@Composable
fun NaverMapScreen(viewModel: NaverMapViewModel) {
    val context = LocalContext.current
    val activity = context as Activity
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val mapView = rememberMapViewWithLifecycle()

    StatusBarIconColor(activity, darkIcons = true)

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(factory = { mapView }) { view ->
            view.getMapAsync { naverMap ->
                viewModel.naverMap = naverMap
                viewModel.setupNaverMap(activity, context, naverMap)
            }
        }

        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.NormalPadding, vertical = Dimens.BigPadding)
                .align(Alignment.TopCenter)
                .height(Dimens.TopBarHeight),
            text = "장소, 주소 검색"
        )

        MyLocationButton(
            modifier = Modifier.align(Alignment.BottomStart),
            onClick = { onClickMyLocationButton(viewModel, context, fusedLocationClient) }
        )
    }
}

@Composable
fun MyLocationButton(modifier: Modifier, onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier
            .padding(
                start = Dimens.NormalPadding,
                bottom = Dimens.BigPadding + Dimens.SmallPadding
            )
            .size(Dimens.NormalFloatingButtonSize),
        containerColor = Color.White,
        contentColor = gray_900

    ) {
        RouteMapIcon(R.drawable.my_location, Dimens.NormalIconSize)
    }
}

private fun onClickMyLocationButton(
    viewModel: NaverMapViewModel,
    context: Context,
    fusedLocationClient: FusedLocationProviderClient
) {
    viewModel.moveToCurrentLocation(context, fusedLocationClient)
}

@Composable
fun SearchBar(modifier: Modifier = Modifier, text: String) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(Dimens.SmallRoundedSize),
        color = Color.White,
        shadowElevation = Dimens.SmallShadowElevation,
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = Dimens.NormalPadding,
                vertical = Dimens.SmallPadding
            ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray,
                modifier = Modifier.weight(Weights.Fill)
            )
            RouteMapIcon(R.drawable.location_1, Dimens.NormalIconSize)
        }
    }
}



