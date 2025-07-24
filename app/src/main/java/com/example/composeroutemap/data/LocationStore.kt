package com.example.composeroutemap.data

import android.location.Location
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.naver.maps.geometry.LatLng

object LocationStore{
    var current by mutableStateOf<LatLng?>(null)

    fun distanceTo(toLat: Double, toLng: Double): Int? {
        val cur = current ?: return null
        val result = FloatArray(1)
        Location.distanceBetween(
            cur.latitude, cur.longitude,
            toLat, toLng,
            result
        )
        return result[0].toInt()
    }
}