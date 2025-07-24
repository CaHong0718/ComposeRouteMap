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
        paddingDp: Int = 48,
        expandRatio: Double = 0.15
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

        // bounds 계산
        val builder = LatLngBounds.Builder()
        points.forEach { builder.include(it) }
        val bounds = builder.build()

        // bounds 확장
        val centerLat = (bounds.southWest.latitude + bounds.northEast.latitude) / 2
        val centerLng = (bounds.southWest.longitude + bounds.northEast.longitude) / 2
        val latSpan = bounds.northEast.latitude - bounds.southWest.latitude
        val lngSpan = bounds.northEast.longitude - bounds.southWest.longitude

        val expandedBounds = LatLngBounds(
            LatLng(
                centerLat - latSpan * (0.5 + expandRatio),
                centerLng - lngSpan * (0.5 + expandRatio)
            ),
            LatLng(
                centerLat + latSpan * (0.5 + expandRatio),
                centerLng + lngSpan * (0.5 + expandRatio)
            )
        )

        val px = (paddingDp * context.resources.displayMetrics.density).toInt()

        naverMap.moveCamera(
            CameraUpdate.fitBounds(expandedBounds, px)
                .animate(CameraAnimation.Easing, 600)
        )
    }
}