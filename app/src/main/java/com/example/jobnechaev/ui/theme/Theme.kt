package com.example.jobnechaev.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = AppColors.Primary,
    background = AppColors.Dark.Background,
    surface = AppColors.Dark.Item,
    onPrimary = AppColors.Dark.TextPrimary,
    onBackground = AppColors.Dark.TextPrimary,
    onSurface = AppColors.Dark.TextPrimary
)

private val LightColorScheme = lightColorScheme(
    primary = AppColors.Primary,
    background = AppColors.Light.Background,
    surface = AppColors.Light.Item,
    onPrimary = AppColors.Light.TextPrimary,
    onBackground = AppColors.Light.TextPrimary,
    onSurface = AppColors.Light.TextPrimary
)

@Composable
fun JobNechaevTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    CompositionLocalProvider(
        LocalTheme provides ThemeState(isDark = darkTheme)
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}