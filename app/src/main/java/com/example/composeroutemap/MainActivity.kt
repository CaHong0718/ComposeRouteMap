package com.example.composeroutemap

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.composeroutemap.ui.map.NaverMapScreen
import com.example.composeroutemap.ui.theme.ComposeRouteMapTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // (1) 패키지명 로그 출력
        val packageName = applicationContext.packageName
        Log.d("NaverMap", "packageName: $packageName")

        // (2) Manifest에 등록된 CLIENT_ID 읽기
        val clientId = try {
            val appInfo = packageManager.getApplicationInfo(packageName, android.content.pm.PackageManager.GET_META_DATA)
            appInfo.metaData.getString("com.naver.maps.map.CLIENT_ID")
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
        Log.d("NaverMap", "CLIENT_ID: $clientId")


        setContent {
            NaverMapScreen()
        }
    }
}