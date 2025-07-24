package com.example.composeroutemap.utils

import kotlin.math.*

data class Point(val lat: Double, val lng: Double)
private const val R = 6371_000.0   // 지구 반지름(m)

/** 두 좌표 간 하버사인 거리(m) */
fun haversine(a: Point, b: Point): Double {
    val dLat = Math.toRadians(b.lat - a.lat)
    val dLng = Math.toRadians(b.lng - a.lng)
    val lat1 = Math.toRadians(a.lat)
    val lat2 = Math.toRadians(b.lat)
    val h = sin(dLat / 2).pow(2) + cos(lat1) * cos(lat2) * sin(dLng / 2).pow(2)
    return 2 * R * asin(sqrt(h))
}

/** (n×n) 거리 행렬 */
fun buildDistanceMatrix(points: List<Point>): Array<DoubleArray> =
    Array(points.size) { i ->
        DoubleArray(points.size) { j ->
            if (i == j) 0.0 else haversine(points[i], points[j])
        }
    }
