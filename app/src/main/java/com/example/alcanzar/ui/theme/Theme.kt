package com.example.alcanzar.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val AlcanzARColors = lightColorScheme(
    primary = AlcanzarPrimary,
    onPrimary = Color.White,
    background = AlcanzarBackground,
    onBackground = AlcanzarTextPrimary,
    surface = AlcanzarSurface,
    onSurface = AlcanzarTextPrimary
)

@Composable
fun AlcanzARTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = AlcanzARColors,
        content = content
    )
}