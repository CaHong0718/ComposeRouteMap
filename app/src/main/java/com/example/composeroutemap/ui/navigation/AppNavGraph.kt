package com.example.composeroutemap.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.composeroutemap.ui.map.NaverMapScreen
import com.example.composeroutemap.ui.map.NaverMapViewModel
import com.example.composeroutemap.ui.map.rememberMapViewWithLifecycle
import com.example.composeroutemap.ui.search.SearchScreen
import com.example.composeroutemap.ui.splash.SplashScreen

@Composable
fun AppNavGraph(navController: NavHostController) {

    // 초기에 생성하여 관리
    val mapView = rememberMapViewWithLifecycle()

    NavHost(navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route)     {
            SplashScreen(
                onReady = { navController.navigate(Screen.Map.route){
                    popUpTo(Screen.Splash.route) {inclusive = true}
                    launchSingleTop = true
                } }
            )
        }

        composable(Screen.Map.route)        {
            val viewModel: NaverMapViewModel = viewModel()

            NaverMapScreen(
                navController = navController,
                viewModel = viewModel,
                mapView = mapView
            )
        }
        composable(Screen.Search.route)     {SearchScreen() }
    }
}