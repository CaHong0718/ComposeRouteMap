package com.example.composeroutemap.data

import androidx.compose.foundation.pager.PagerSnapDistance
import java.util.UUID

data class Place(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val category: String,
    val roadAddress: String,
    val lat: Double?,
    val lng: Double?,
    val distanceMeter: Int? = null
)