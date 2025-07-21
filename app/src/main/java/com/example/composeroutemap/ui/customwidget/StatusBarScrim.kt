package com.example.composeroutemap.ui.customwidget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun StatusBarScrim(modifier: Modifier = Modifier, color: Color) {
    Box(
        modifier
            .fillMaxWidth()
            .background(color)
            .windowInsetsPadding(WindowInsets.statusBars)
    )
}