package com.alifwyaa.azanmunich.android.ui.theme

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable


/**
 * App theme
 */
@Composable
fun AppTheme(
    isDarkTheme: Boolean,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = getThemeColors(isDarkTheme),
        typography = AppTypography,
        shapes = AppShapes,
        content = content
    )
}

private fun getThemeColors(darkColors: Boolean) = Colors(
    background = appColors(darkColors = darkColors).background,
    surface = appColors(darkColors = darkColors).surface,
    error = appColors(darkColors = darkColors).error,
    primaryVariant = appColors(darkColors = darkColors).primaryVariant,
    primary = appColors(darkColors = darkColors).primary,
    onPrimary = appColors(darkColors = darkColors).onPrimary,
    secondaryVariant = appColors(darkColors = darkColors).primaryVariant,
    secondary = appColors(darkColors = darkColors).primary,
    onSecondary = appColors(darkColors = darkColors).onPrimary,
    onBackground = appColors(darkColors = darkColors).onBackground,
    onSurface = appColors(darkColors = darkColors).onSurface,
    onError = appColors(darkColors = darkColors).onError,
    isLight = darkColors.not(),
)
