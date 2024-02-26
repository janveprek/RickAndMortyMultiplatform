package com.veprek.honza.rickandmorty.design.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme

// val ColorScheme.divider: Color
//    get() = if (isSystemInDarkTheme()) Color(0xFF474749) else Color(0xFFF0F0F0)

internal val lightColors =
    lightColorScheme(
        primary = primaryLight,
        onPrimary = onPrimaryLight,
        primaryContainer = primaryContainerLight,
        onPrimaryContainer = onPrimaryContainerLight,
        surface = surfaceLight,
        surfaceVariant = surfaceVariantLight,
        onSurfaceVariant = onSurfaceVariantLight,
        background = backgroundLight,
        onBackground = onBackgroundLight,
        secondary = secondaryLight,
        onSecondary = onSecondaryLight,
        secondaryContainer = secondaryContainerLight,
        onSecondaryContainer = onSecondaryContainerLight,
    )

internal val darkColors =
    darkColorScheme(
        primary = primaryDark,
        onPrimary = onPrimaryDark,
        primaryContainer = primaryContainerLight,
        onPrimaryContainer = onPrimaryContainerLight,
        surface = surfaceDark,
        surfaceVariant = surfaceVariantDark,
        onSurfaceVariant = onSurfaceVariantDark,
        background = backgroundDark,
        onBackground = onBackgroundDark,
        secondary = secondaryDark,
        onSecondary = onSecondaryDark,
        tertiary = tertiaryLight,
        onTertiary = onTertiaryLight,
        secondaryContainer = secondaryContainerDark,
        onSecondaryContainer = onSecondaryContainerDark,
    )
