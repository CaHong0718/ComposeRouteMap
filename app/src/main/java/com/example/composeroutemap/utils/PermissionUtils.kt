package com.example.composeroutemap.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

import androidx.core.content.ContextCompat
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts

val requiredPermissions = arrayOf(
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.ACCESS_COARSE_LOCATION
)

fun hasLocationPermission(context: Context): Boolean {
    return requiredPermissions.all {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }
}

suspend fun requestLocationPermissionsIfNeeded(activity: ComponentActivity): Boolean{
    if (hasLocationPermission(activity)) return true

    val deferred = CompletableDeferred<Boolean>()

    val launcher = activity.registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ){ result ->
        deferred.complete(result.values.all { it })
    }

    launcher.launch(requiredPermissions)
    return deferred.await()
}