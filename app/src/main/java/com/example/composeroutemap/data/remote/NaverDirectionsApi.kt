package com.example.composeroutemap.data.remote

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NaverDirectionsApiService {
    @GET("map-direction/v1/driving")
    suspend fun driving(
        @Header("X-NCP-APIGW-API-KEY-ID") clientId: String,
        @Header("X-NCP-APIGW-API-KEY") clientSecret: String,
        @Query("start") start: String,      // lng,lat
        @Query("goal") goal: String,
        @Query("waypoints") waypoints: String?,
        @Query("option") option: String = "trafast"
    ): DirectionsDto
}