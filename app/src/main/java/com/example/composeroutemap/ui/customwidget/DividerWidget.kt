package com.example.composeroutemap.ui.customwidget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.composeroutemap.data.Dimens

@Composable
fun DividerWidget(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.DividerHeight)
            .background(Color.Gray)
    )
}