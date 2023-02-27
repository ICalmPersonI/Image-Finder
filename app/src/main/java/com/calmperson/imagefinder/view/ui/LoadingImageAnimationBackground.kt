package com.calmperson.imagefinder.view.ui

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color

@Composable
fun createImageLoadingAnimation(): Color {
    val infiniteTransition = rememberInfiniteTransition()
    val color by infiniteTransition.animateColor(
        initialValue = MaterialTheme.colors.primary,
        targetValue = MaterialTheme.colors.onPrimary,
        animationSpec = infiniteRepeatable(
            tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    return color
}