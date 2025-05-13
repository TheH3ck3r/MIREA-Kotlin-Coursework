package com.example.jobnechaev.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object AppColors {
    val Primary = Color(0xFFFF9900)
    
    object Dark {
        val Background = Color(0xFF121212)
        val BackgroundSecondary = Color(0xFF1A1A1A)
        val Item = Color(0xFF1E1E1E)
        val TextPrimary = Color(0xFFFFFFFF)
        val TextSecondary = Color(0xFFB3B3B3)
        val TextDisabled = Color(0xFF666666)
    }

    object Light {
        val Background = Color(0xFFF5F5F5)
        val BackgroundSecondary = Color(0xFFEEEEEE)
        val Item = Color(0xFFFFFFFF)
        val TextPrimary = Color(0xFF000000)
        val TextSecondary = Color(0xFF666666)
        val TextDisabled = Color(0xFFB3B3B3)
    }

    val Background @Composable get() = if (LocalTheme.current.isDark) Dark.Background else Light.Background
    val BackgroundSecondary @Composable get() = if (LocalTheme.current.isDark) Dark.BackgroundSecondary else Light.BackgroundSecondary
    val Item @Composable get() = if (LocalTheme.current.isDark) Dark.Item else Light.Item
    val TextPrimary @Composable get() = if (LocalTheme.current.isDark) Dark.TextPrimary else Light.TextPrimary
    val TextSecondary @Composable get() = if (LocalTheme.current.isDark) Dark.TextSecondary else Light.TextSecondary
    val TextDisabled @Composable get() = if (LocalTheme.current.isDark) Dark.TextDisabled else Light.TextDisabled
} 