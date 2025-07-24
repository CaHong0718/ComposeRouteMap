package com.example.composeroutemap.data.repository

import android.content.Context
import com.example.composeroutemap.data.Place
import com.example.composeroutemap.data.remote.NaverSearchNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlaceRepository(private val appContext: Context) {

    private val api get() = NaverSearchNetwork.api(appContext)

    suspend fun search(query: String): Result<List<Place>> = withContext(Dispatchers.IO) {
        runCatching { api.searchPlaces(query).items.map { it.toDomain() } }
    }
}