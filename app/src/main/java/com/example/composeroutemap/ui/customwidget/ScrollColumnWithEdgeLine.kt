package com.example.composeroutemap.ui.customwidget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.composeroutemap.data.Dimens
import com.example.composeroutemap.data.Weights
import com.example.composeroutemap.ui.theme.gray_300

@Composable
fun ScrollColumnWithEdgeLine(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    content: @Composable (ColumnScope.() -> Unit),
) {
    val scrollState = rememberScrollState()

    val showTopLine by remember {
        derivedStateOf { scrollState.value > 0 }
    }

    val showBottomLine by remember {
        derivedStateOf { scrollState.value < scrollState.maxValue }
    }

    Column(modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.DividerHeight)
                .background(
                    if (showTopLine) gray_300 else Color.Transparent
                )
        )

        /** 스크롤 영역 */
        Column(
            modifier = Modifier
                .weight(Weights.Fill)
                .verticalScroll(scrollState)
                .padding(contentPadding),
            content = content
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.DividerHeight)
                .background(
                    if (showBottomLine) gray_300 else Color.Transparent
                )
        )
    }
}