package com.example.valorantapi.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = ValorantRed,
    background = ValorantDark,
    surfaceVariant = ValorantCard,
    onBackground = ValorantWhite,
    onSurface = ValorantWhite,
    onPrimary = Color.Black
)

@Composable
fun ValorantAPITheme(
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme
    MaterialTheme(colorScheme = colorScheme, typography = valorantTypography, content = content)
}