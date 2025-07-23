package com.example.composeroutemap.data

import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import com.example.composeroutemap.R
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage



object MapMarkers {
    val myLocationMarker: Marker = Marker().apply {
        icon = OverlayImage.fromResource(R.drawable.position_mark)
        width = Dimens.MyLocationMarkerSize
        height = Dimens.MyLocationMarkerSize
    }
}
