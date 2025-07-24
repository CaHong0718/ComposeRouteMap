package com.example.composeroutemap.data

import android.content.res.Resources
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.composeroutemap.R
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import kotlin.math.roundToInt


object MapMarkers {
    val myLocationMarker: Marker = Marker().apply {
        icon = OverlayImage.fromResource(R.drawable.position_mark)
        width = Dimens.MyLocationMarkerSize.value.dpToPx()
        height = Dimens.MyLocationMarkerSize.value.dpToPx()
    }
}

fun Float.dpToPx(): Int =
    (this * Resources.getSystem().displayMetrics.density).roundToInt()
