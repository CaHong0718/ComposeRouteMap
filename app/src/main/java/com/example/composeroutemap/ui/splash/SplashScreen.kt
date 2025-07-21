package com.example.composeroutemap.ui.splash

import android.util.Log
import android.window.SplashScreen
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composeroutemap.data.LocationStore
import com.example.composeroutemap.utils.requestLocationPermissionsIfNeeded

@Composable
fun SplashScreen(onReady:() -> Unit){
    val activity = LocalContext.current as ComponentActivity
    val viewModel: SplashViewModel = viewModel()

    // 위치 권한 + 위치 요청
    LaunchedEffect(Unit) {
        val granted = requestLocationPermissionsIfNeeded(activity)
        if (granted) viewModel.fetchCurrentLocation() else viewModel.skip()
    }

    // ready Flag 가 True -> 화면 전환
    LaunchedEffect(viewModel.ready) { if(viewModel.ready) onReady() }
    //Todo: 최소 시간이 필요해 보임. 1~2초 생각중

    //Todo: Ui 만들기
}