package com.example.composeroutemap.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat

import androidx.core.content.ContextCompat
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import com.example.composeroutemap.data.LocationStore

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

fun checkLocationPermissionAnd(context: Context, function: () -> Unit){
    if (hasLocationPermission(context)) {
        function()
    } else {
        Toast.makeText(context, "위치 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
    }
}