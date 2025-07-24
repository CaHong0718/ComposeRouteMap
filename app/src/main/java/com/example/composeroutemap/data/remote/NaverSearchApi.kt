package com.example.composeroutemap.data.remote

import android.view.Display
import retrofit2.http.GET
import retrofit2.http.Query


interface NaverSearchApi{
    @GET("v1/search/local.json")
    suspend fun searchPlaces(
        @Query("query") query: String,
        @Query("display") display: Int = 20,
        @Query("start") start: Int = 1,
        @Query("sort") sort: String = "random"
    ): PlaceSearchResponse
}