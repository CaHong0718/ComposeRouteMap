package com.example.composeroutemap.data.remote

import com.example.composeroutemap.data.Place
import com.google.gson.annotations.SerializedName
import com.naver.maps.geometry.LatLng

data class PlaceSearchResponse(
    val total: Int,
    val display: Int,
    val items: List<PlaceDto>
)

data class PlaceDto(
    val title: String,
    val category: String,
    val address: String,
    @SerializedName("roadAddress") val roadAddress: String,
    val link: String,
    val mapx: String, // 경도
    val mapy: String // 위도
){
    fun toDomain(): Place {
        val lon = mapx.toDoubleOrNull()?.div(10_000_000)   // 126.xxx
        val lat = mapy.toDoubleOrNull()?.div(10_000_000)   // 37.xxx

        return Place(
            name        = title.replace(Regex("<[/]?b>"), ""),
            category    = category,
            roadAddress = roadAddress.ifBlank { address },
            lat         = lat,
            lng         = lon
        )
    }
}

data class DirectionsDto(
    val route: Route
) {
    data class Route(
        val trafast: List<Trafast>
    )

    data class Trafast(
        val summary: Summary,
        val path: List<List<Double>>
    )

    data class Summary(
        val distance: Int,
        val duration: Int
    )
}

data class RouteSearchResult(
    val routes: List<LatLng>,
    val distance: Int,
    val duration: Int
)