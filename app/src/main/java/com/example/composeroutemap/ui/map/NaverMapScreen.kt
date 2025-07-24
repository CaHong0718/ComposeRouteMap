package com.example.composeroutemap.ui.map

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
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
import com.example.composeroutemap.data.Weights
import com.example.composeroutemap.data.dpToPx
import com.example.composeroutemap.ui.customwidget.RouteMapIcon
import com.example.composeroutemap.ui.customwidget.StatusBarIconColor
import com.example.composeroutemap.ui.customwidget.rememberHapticClick
import com.example.composeroutemap.ui.navigation.Screen
import com.example.composeroutemap.ui.search.SearchViewModel
import com.example.composeroutemap.ui.theme.*
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
    var pathOverlay by remember { mutableStateOf<PathOverlay?>(null) }


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

            viewModel.placeMarkers.forEach{ it.map = null}
            viewModel.placeMarkers.clear()

            // 새 마커 추가.
            places.forEach{ p ->
                Marker().apply {
                    position = LatLng(p.lat!!, p.lng!!)
                    captionText = p.name
                    map = naverMap
                    width = Dimens.SearchedMarkerSize.value.dpToPx()
                    height = Dimens.SearchedMarkerSize.value.dpToPx()
                    icon = OverlayImage.fromResource(R.drawable.marker)
                    viewModel.placeMarkers += this
                }
            }
        }

        /** 경로 그리기 */
        LaunchedEffect(polyline, viewModel.naverMap) {
            val naverMap = viewModel.naverMap ?: return@LaunchedEffect
            pathOverlay?.map = null
            if (polyline.size >= 2) {
                pathOverlay = PathOverlay().apply {
                    coords = polyline
                    color  = android.graphics.Color.GRAY
                    width  = 20
                    map    = naverMap

                    patternImage    = OverlayImage.fromResource(R.drawable.arrow_tile)
                    patternInterval = 80
                }
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

        if (isLoading){
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



