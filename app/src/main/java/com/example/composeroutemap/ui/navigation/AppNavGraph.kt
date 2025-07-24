package com.example.composeroutemap.ui.navigation

import androidx.compose.animation.core.tween
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
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Left
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Right
import com.example.composeroutemap.data.AnimationDelay
import com.example.composeroutemap.ui.customwidget.RouteMapIcon
import com.example.composeroutemap.ui.search.PlaceSearchScreen
import com.example.composeroutemap.ui.search.PlaceSearchViewModel
import com.example.composeroutemap.ui.search.SearchViewModel

@Composable
fun AppNavGraph(navController: NavHostController) {

    // 초기에 생성하여 관리
    val mapView = rememberMapViewWithLifecycle()
    val searchVm: SearchViewModel = viewModel()


    NavHost(navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onReady = {
                    navController.navigate(Screen.Map.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                        launchSingleTop = true
                    }
                })
        }

        composable(route = Screen.Map.route, exitTransition = {
            slideOutOfContainer(Left, tween(AnimationDelay.NormalDelay))
        }, popEnterTransition = {
            slideIntoContainer(Right, tween(AnimationDelay.NormalDelay))
        }) {
            val viewModel: NaverMapViewModel = viewModel()

            NaverMapScreen(
                navController = navController, viewModel = viewModel, mapView = mapView
            )
        }

        composable(route = Screen.Search.route, enterTransition = {
            slideIntoContainer(Left, tween(AnimationDelay.NormalDelay))
        }, popExitTransition = {
            slideOutOfContainer(Right, tween(AnimationDelay.NormalDelay))
        }) { SearchScreen(navController = navController, viewModel = searchVm) }

        composable(route = Screen.PlaceSearch.route) {
            PlaceSearchScreen(navController = navController, onPlaceSelected = searchVm::addPlace)
        }
    }
}