package com.tengyeekong.githubusers.ui.common.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
fun getTypography(enableDarkMode: Boolean): Typography {
    val color = if (enableDarkMode) Color.White else Color.DarkGray
    return Typography(
        body1 = TextStyle(color = color, fontSize = 12.sp),
        subtitle1 = TextStyle(
            color = color,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        ),
        h1 = TextStyle(
            color = color,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        ),
        button = TextStyle(
            color = color,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    )
}