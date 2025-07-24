package com.example.composeroutemap.data

data class Place(
    val name: String,
    val category: String,
    val roadAddress: String,
    val lat: Double?,
    val lng: Double?
)