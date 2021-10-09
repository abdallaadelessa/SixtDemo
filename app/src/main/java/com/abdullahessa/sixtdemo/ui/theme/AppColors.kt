package com.alifwyaa.azanmunich.android.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * App colors
 */
fun appColors(darkColors: Boolean): AppColors = if (darkColors) AppDarkColors else AppLightColors

/**
 * App colors interface
 */
interface AppColors {
    val background: Color
    val surface: Color
    val error: Color
    val surfaceBorder: Color
    val primary: Color
    val primaryVariant: Color
    val onPrimary: Color
    val onBackground: Color
    val onSurface: Color
    val onError: Color
}

private object AppLightColors : AppColors {
    override val background = Color(0xffc5f5ed)
    override val surface = Color.Transparent
    override val error: Color = Color(0xFFB00020)
    override val surfaceBorder = Color.Black
    override val primaryVariant = Color(0xff27ceaf)
    override val primary = Color(0xff3ddcc4)
    override val onPrimary: Color = Color.Black
    override val onBackground: Color = Color.Black
    override val onSurface: Color = Color.Black
    override val onError: Color = Color.White
}

private object AppDarkColors : AppColors {
    override val background = Color(0xff000000)
    override val surface = Color(0xff202020)
    override val error: Color = Color(0xFFCF6679)
    override val surfaceBorder = Color.Transparent
    override val primaryVariant = Color(0xff939292)
    override val primary = Color(0xff202020)
    override val onPrimary: Color = Color.White
    override val onBackground: Color = Color.White
    override val onSurface: Color = Color.White
    override val onError: Color = Color.Black
}
