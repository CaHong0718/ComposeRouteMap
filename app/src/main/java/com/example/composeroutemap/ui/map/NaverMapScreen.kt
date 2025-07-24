package com.example.composeroutemap.ui.map

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.composeroutemap.R
import com.example.composeroutemap.data.Dimens
import com.example.composeroutemap.data.LocationStore
import com.example.composeroutemap.data.Weights
import com.example.composeroutemap.data.dpToPx
import com.example.composeroutemap.ui.customwidget.RouteMapIcon
import com.example.composeroutemap.ui.customwidget.StatusBarIconColor
import com.example.composeroutemap.ui.customwidget.rememberHapticClick
import com.example.composeroutemap.ui.navigation.Screen
import com.example.composeroutemap.ui.search.SearchViewModel
import com.example.composeroutemap.ui.theme.*
import com.example.composeroutemap.utils.MapUtils
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.MapView
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.overlay.PathOverlay


@Composable
fun NaverMapScreen(
    navController: NavController,
    viewModel: NaverMapViewModel,
    mapView: MapView,
    searchVm: SearchViewModel
) {
    val context = LocalContext.current
    val activity = context as Activity
    val places by searchVm.places.collectAsState()
    val isLoading by searchVm.loading.collectAsState()
    val polyline by searchVm.polyline.collectAsState()
    val distance by searchVm.distance.collectAsState()
    val duration by searchVm.duration.collectAsState()


    StatusBarIconColor(activity, darkIcons = true)

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(factory = { mapView }) { view ->
            view.getMapAsync { naverMap ->
                viewModel.naverMap = naverMap
                viewModel.setupNaverMap(context, naverMap)
            }
        }


        /** 장소가 변경 될때 마커 갱신*/
        LaunchedEffect(places, viewModel.naverMap) {
            val naverMap = viewModel.naverMap ?: return@LaunchedEffect

            viewModel.placeMarkers.forEach { it.map = null }
            viewModel.placeMarkers.clear()

            // 새 마커 추가 & 좌표 수집
            val allPoints = buildList<LatLng> {
                places.forEach { p ->
                    val latLng = LatLng(p.lat!!, p.lng!!)
                    add(latLng)                           // 카메라용 좌표 축적

                    viewModel.placeMarkers += Marker().apply {
                        position = latLng
                        captionText = p.name
                        icon = OverlayImage.fromResource(R.drawable.marker)
                        width = Dimens.SearchedMarkerSize.value.dpToPx()
                        height = Dimens.SearchedMarkerSize.value.dpToPx()
                        map = naverMap
                    }
                }
                add(LatLng(LocationStore.current?.latitude!!, LocationStore.current?.longitude!!))
            }

            MapUtils.moveCameraToBounds(allPoints, naverMap, context)
        }

        /** 경로 그리기 */
        LaunchedEffect(polyline, viewModel.naverMap) {
            val nMap = viewModel.naverMap ?: return@LaunchedEffect

            viewModel.currentOverlay.map = null

            if (polyline.size >= 2) {
                viewModel.currentOverlay.coords = polyline
                viewModel.currentOverlay.map = nMap
            }
        }

        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = Dimens.NormalPadding,
                    vertical = Dimens.LargePadding + Dimens.SmallPadding
                )
                .align(Alignment.TopCenter)
                .height(Dimens.TopBarHeight),
            text = "장소, 주소 검색",
            onClick = rememberHapticClick(onClick = { onClickSearchBar(navController) })
        )

        MyLocationButton(
            modifier = Modifier.align(Alignment.BottomStart),
            onClick = { onClickMyLocationButton(viewModel, context) }
        )

        if (isLoading) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(loading_background)           // 반투명 블랙
            ) {
                CircularProgressIndicator(
                    Modifier
                        .align(Alignment.Center)
                        .size(Dimens.LargeIconSize),
                    color = gray_50
                )
            }
        }

        duration?.let {
            RouteInfoBar(
                distance = distance!!,
                duration = duration!!,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = Dimens.LargePadding+Dimens.SmallPadding, end = Dimens.NormalPadding),
            )
        }
    }
}

@Composable
fun RouteInfoBar(
    distance: Int,
    duration: Int,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(Dimens.NormalRoundedSize),
        shadowElevation = Dimens.SmallShadowElevation,
    ) {
        Row(
            Modifier.padding(horizontal = Dimens.NormalPadding, vertical = Dimens.NormalPadding),
            horizontalArrangement = Arrangement.spacedBy(Dimens.NormalPadding),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(distance.formatDistance(), style = MaterialTheme.typography.labelLarge)
            Spacer(Modifier.height(Dimens.NormalPadding))
            Text(duration.formatDuration(), style = MaterialTheme.typography.labelLarge)
        }
    }
}

@Composable
fun MyLocationButton(modifier: Modifier, onClick: () -> Unit) {
    FloatingActionButton(
        onClick = rememberHapticClick(onClick),
        modifier = modifier
            .padding(
                start = Dimens.NormalPadding,
                bottom = Dimens.LargePadding + Dimens.SmallPadding
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
) {
    viewModel.moveToCurrentLocation(context)
}

@Composable
fun SearchBar(modifier: Modifier = Modifier, text: String, onClick: () -> Unit) {
    val interaction = remember { MutableInteractionSource() }
    Surface(
        modifier = modifier.clickable(
            interactionSource = interaction,
            indication = null,
        ) { onClick() },
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

private fun onClickSearchBar(navController: NavController) {
    navController.navigate(Screen.Search.route)
}

/** 거리(m) → “1.3 km / 850 m” */
private fun Int.formatDistance(): String =
    if (this >= 1000) "%.1f km".format(this / 1000f) else "${this} m"

/** 시간(ms) → “17분” “1시간 05분” */
private fun Int.formatDuration(): String {
    val totalMin = this / 1000 / 60
    val h = totalMin / 60
    val m = totalMin % 60
    return if (h > 0) "${h}시간 ${m}분" else "${m}분"
}


