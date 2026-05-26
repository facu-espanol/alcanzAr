package com.example.alcanzar.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val AlcanzARColors = lightColorScheme(
    primary = Color(0xFF1976D2),   // azul
    onPrimary = Color.White,
    background = Color.White,
    onBackground = Color.Black
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