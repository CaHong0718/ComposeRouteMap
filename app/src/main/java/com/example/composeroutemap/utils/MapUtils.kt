package com.example.composeroutemap.utils

import android.content.Context
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.NaverMap

object MapUtils {
    fun moveCameraToBounds(
        points: List<LatLng>,
        naverMap: NaverMap?,
        context: Context,
        paddingDp: Int = 48
    ) {
        if (naverMap == null || points.isEmpty()) return

        // 단일 포인트라면 확대 + 스크롤
        if (points.size == 1) {
            naverMap.moveCamera(
                CameraUpdate.scrollAndZoomTo(points.first(), 16.0)
                    .animate(CameraAnimation.Easing)
            )
            return
        }

        // 여러 포인트 → LatLngBounds 계산
        val bounds = LatLngBounds.Builder().apply {
            points.forEach { include(it) }
        }.build()

        // dp → px 변환
        val px = (paddingDp * context.resources.displayMetrics.density).toInt()

        naverMap.moveCamera(
            CameraUpdate.fitBounds(bounds, px)
                .animate(CameraAnimation.Easing, 600)
        )
    }
}