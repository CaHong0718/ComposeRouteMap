package com.example.composeroutemap.ui.customwidget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.composeroutemap.data.Dimens
import com.example.composeroutemap.ui.theme.gray_900
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.composeroutemap.data.FontSize
import com.example.composeroutemap.ui.theme.gray_50

@Composable
fun NextButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    backgroundColor: Color = gray_900,
    textColor: Color = gray_50,
    text: String = "계속하기"
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(Dimens.SmallRoundedSize)
            )
            .clickable(onClick = onClick)
            .height(Dimens.NormalButtonHeight),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
            ),
            textAlign = TextAlign.Center
        )
    }
}