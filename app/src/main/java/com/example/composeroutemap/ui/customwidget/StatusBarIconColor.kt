package com.example.composeroutemap.ui.customwidget

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat


@Composable
fun StatusBarIconColor(activity: Activity, darkIcons: Boolean) {
    val window = activity.window
    SideEffect {
        WindowCompat.setDecorFitsSystemWindows(window, false)

        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars =
            darkIcons
    }
}