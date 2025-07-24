package com.example.composeroutemap.data.remote

import com.example.composeroutemap.data.Place
import com.google.gson.annotations.SerializedName

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
    val mapx: String, // 경도 TODO: LatLng 표기로 변수명 변경
    val mapy: String // 위도
){
    fun toDomain() = Place(
        name = title.replace(Regex("<[/]?b>"), ""), // <b> 태그 제거
        category = category,
        roadAddress = roadAddress.ifBlank { address },
        lat = mapy.toDoubleOrNull()?.div(1e6),
        lng = mapx.toDoubleOrNull()?.div(1e6)
    )
}