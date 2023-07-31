package com.tengyeekong.githubusers.ui.common.theme

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.defaultShimmerTheme

@Composable
fun ComposeTheme(
    enableDarkMode: MutableState<Boolean>,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (enableDarkMode.value) darkColorPalette else lightColorPalette,
        typography = getTypography(enableDarkMode.value),
        shapes = shapes,
        content = content
    )
}

private val lightColorPalette = lightColors(
    primary = colorPrimary,
    primaryVariant = colorPrimary,
    secondary = Color.White,
    background = Color.White,
    surface = colorPrimaryLight,
)

private val darkColorPalette = darkColors(
    primary = colorPrimary,
    primaryVariant = colorPrimary,
    secondary = Color.Black,
    background = colorDarkBackground,
    surface = colorPrimaryDark,
)

val shimmerTheme = defaultShimmerTheme.copy(
    animationSpec = infiniteRepeatable(
        animation = tween(
            durationMillis = 1_000,
            delayMillis = 1_500,
            easing = LinearEasing,
        ),
    ),
    blendMode = BlendMode.SrcAtop,
    rotation = 340f,
    shaderColors = listOf(
        Color.Transparent,
        Color.White.copy(alpha = 0.4f),
        Color.Transparent,
    ),
    shaderColorStops = null,
    shimmerWidth = 100.dp,
)

val shimmerDarkTheme = shimmerTheme.copy(
    shaderColors = listOf(
        Color.Transparent,
        Color.White.copy(alpha = 0.2f),
        Color.Transparent,
    ),
)
