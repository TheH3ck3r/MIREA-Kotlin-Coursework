package com.example.jobnechaev.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf

data class ThemeState(
    val isDark: Boolean
)

val LocalTheme = staticCompositionLocalOf { ThemeState(isDark = true) } 