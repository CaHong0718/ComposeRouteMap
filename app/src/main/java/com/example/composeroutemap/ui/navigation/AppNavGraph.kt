package com.example.composeroutemap.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.composeroutemap.ui.map.NaverMapScreen
import com.example.composeroutemap.ui.map.NaverMapViewModel
import com.example.composeroutemap.ui.search.SearchScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.Map.route) {
        composable(Screen.Map.route)        {
            val viewModel: NaverMapViewModel = viewModel()

            NaverMapScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
        composable(Screen.Search.route)     {SearchScreen() }
    }
}