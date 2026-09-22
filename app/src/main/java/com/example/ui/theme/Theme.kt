package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = DriveeNavy,
    onPrimary = Color.White,
    primaryContainer = DriveeSurfaceLow,
    onPrimaryContainer = DriveeNavy,
    secondary = DriveeOrange,
    onSecondary = Color.White,
    secondaryContainer = DriveeOrangeContainer,
    onSecondaryContainer = DriveeOrangeDark,
    background = DriveeBackground,
    onBackground = DriveeTextPrimary,
    surface = DriveeSurface,
    onSurface = DriveeTextPrimary,
    surfaceVariant = DriveeSurfaceLow,
    onSurfaceVariant = DriveeTextSecondary,
    outline = DriveeCardBorder
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
