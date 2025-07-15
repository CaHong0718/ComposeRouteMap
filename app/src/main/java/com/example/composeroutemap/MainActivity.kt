package com.example.composeroutemap

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composeroutemap.ui.map.MapViewModel
import com.example.composeroutemap.ui.map.NaverMapScreen
import com.example.composeroutemap.ui.theme.ComposeRouteMapTheme
import com.example.composeroutemap.utils.hasLocationPermission
import com.example.composeroutemap.utils.requestLocationPermissionsIfNeeded
import com.example.composeroutemap.utils.requiredPermissions


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ComposeRouteMapTheme{
                val viewModel: MapViewModel = viewModel()
                val context = LocalContext.current

                // 앱 시작 시 실행
                LaunchedEffect(Unit) {
                    requestLocationPermissionsIfNeeded(this@MainActivity)
                }

                NaverMapScreen(viewModel = viewModel)
            }
        }
    }
}