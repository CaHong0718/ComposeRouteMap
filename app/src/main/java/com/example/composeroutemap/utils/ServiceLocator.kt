package com.example.composeroutemap.utils

import com.example.composeroutemap.BuildConfig
import com.example.composeroutemap.data.remote.NaverDirectionsApiService
import com.example.composeroutemap.data.repository.RouteRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceLocator {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://maps.apigw.ntruss.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val directionsApi: NaverDirectionsApiService by lazy {
        retrofit.create(NaverDirectionsApiService::class.java)
    }


    val routeRepository: RouteRepository by lazy {
        RouteRepository(
            api = directionsApi,
            clientId     = BuildConfig.NAVER_MAP_API_CLIENT_ID,
            clientSecret = BuildConfig.NAVER_MAP_API_CLIENT_SECRET
        )
    }
}