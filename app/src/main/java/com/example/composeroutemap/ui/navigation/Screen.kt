package com.example.composeroutemap.ui.navigation

import okhttp3.Route

sealed class Screen(val route: String){
    data object Splash      : Screen("splash")
    data object Map         : Screen("map")
    data object Search      : Screen("search")
}