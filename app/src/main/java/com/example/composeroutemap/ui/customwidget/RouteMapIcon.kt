package com.example.composeroutemap.ui.customwidget

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp

@Composable
fun RouteMapIcon(drawable: Int, size: Dp){
    Icon(
        painter = painterResource(id = drawable),
        contentDescription = null,
        modifier = Modifier.size(size),
    )
}