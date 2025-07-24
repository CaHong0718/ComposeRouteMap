package com.example.composeroutemap.data.repository

import com.example.composeroutemap.data.Place
import com.example.composeroutemap.data.remote.DirectionsDto
import com.example.composeroutemap.data.remote.NaverDirectionsApiService
import com.example.composeroutemap.domain.algorithm.heldKarp
import com.example.composeroutemap.utils.Point
import com.example.composeroutemap.utils.buildDistanceMatrix
import com.naver.maps.geometry.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RouteRepository(
    private val api: NaverDirectionsApiService,
    private val clientId: String,
    private val clientSecret: String
) {

    /** 1) 거리 기준 최적 순서 → 2) Directions 5/15 한 번 호출 → polyline */
    suspend fun calcRoutePolyline(all: List<Place>): List<LatLng> =
        withContext(Dispatchers.Default) {
            /* --- 1. 거리행렬 & Held-Karp --- */
            val pts = all.map { Point(it.lat!!, it.lng!!) }
            val dist = buildDistanceMatrix(pts)
            val order = heldKarp(dist)      // 0 … k  (0 = 내 위치)

            /* --- 2. Directions API 호출(스레드 전환) --- */
            val coords = order.map { all[it] }
            val start = "${coords.first().lng},${coords.first().lat}"
            val goal = "${coords.last().lng},${coords.last().lat}"
            val waypoints = coords
                .drop(1).dropLast(1)
                .joinToString("|") { "${it.lng},${it.lat}" }
                .takeIf { it.isNotEmpty() }

            // 장소 수에 따라 Directions5/15 자동 선택
            val resp: DirectionsDto = withContext(Dispatchers.IO) {
                api.driving(
                    clientId     = clientId,
                    clientSecret = clientSecret,
                    start  = start,
                    goal   = goal,
                    waypoints = waypoints,
                    option = "trafast"
                )
            }

            /* --- 3. 응답 → Polyline 좌표 변환 --- */
            val path = resp.route.trafast[0].path         // [[lng,lat],…]
            path.map { LatLng(it[1], it[0]) }             // 최종 반환
        }
}