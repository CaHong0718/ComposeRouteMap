package com.example.composeroutemap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.composeroutemap.ui.map.NaverMapViewModel
import com.example.composeroutemap.ui.map.NaverMapScreen
import com.example.composeroutemap.ui.navigation.AppNavGraph
import com.example.composeroutemap.ui.theme.ComposeRouteMapTheme
import com.example.composeroutemap.utils.requestLocationPermissionsIfNeeded


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ComposeRouteMapTheme{
                val navController = rememberNavController()

                // 앱 시작 시 실행
                LaunchedEffect(Unit) {
                    requestLocationPermissionsIfNeeded(this@MainActivity)
                }

                AppNavGraph(navController)
            }
        }
    }
}