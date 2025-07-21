package com.example.composeroutemap.ui.navigation

import okhttp3.Route

sealed class Screen(val route: String){
    object Map      : Screen("map")
    object Search   : Screen("search")
}