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
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.composeroutemap.data.LocationStore
import kotlinx.coroutines.suspendCancellableCoroutine

val requiredPermissions = arrayOf(
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.ACCESS_COARSE_LOCATION
)

fun hasLocationPermission(context: Context): Boolean {
    return requiredPermissions.all {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }
}

suspend fun requestLocationPermissionsIfNeeded(activity: ComponentActivity): Boolean {
    if (hasLocationPermission(activity)) return true

    return suspendCancellableCoroutine { cont ->
        // 고유 키 — 중복 방지
        val key = "req_perm_${System.currentTimeMillis()}"

        var launcher: ActivityResultLauncher<Array<String>>? = null

        launcher = activity.activityResultRegistry.register(
            key,
            ActivityResultContracts.RequestMultiplePermissions()
        ) { result ->
            val granted = result.values.all { it }
            if (cont.isActive) cont.resume(granted) {}

            launcher?.unregister()
            launcher = null
        }

        launcher?.launch(requiredPermissions)

        cont.invokeOnCancellation { launcher?.unregister() }
    }
}

fun checkLocationPermissionAnd(context: Context, function: () -> Unit) {
    if (hasLocationPermission(context)) {
        function()
    } else {
        Toast.makeText(context, "위치 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
    }
}