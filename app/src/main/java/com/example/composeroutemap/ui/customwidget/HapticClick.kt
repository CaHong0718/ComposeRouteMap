package com.example.composeroutemap.ui.customwidget

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback

@Composable
fun rememberHapticClick(
    onClick: () -> Unit,
    type: HapticFeedbackType = HapticFeedbackType.ContextClick
): () -> Unit {
    val haptic = LocalHapticFeedback.current

    return remember(onClick, haptic) {
        {
            haptic.performHapticFeedback(type)
            onClick()
        }
    }
}