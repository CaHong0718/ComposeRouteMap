package com.example.composeroutemap.data

import androidx.compose.foundation.pager.PagerSnapDistance

data class Place(
    val name: String,
    val category: String,
    val roadAddress: String,
    val lat: Double?,
    val lng: Double?,
    val distanceMeter: Int? = null
)