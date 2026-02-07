package com.example.starwarsentitymanagementsystem.ui.base.common

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * 1. Default Spacing
 * Objeto Singleton con los valores por defecto
 */
object DefaultSpacing {
    val small = 8.dp
    val medium = 16.dp
    val large = 24.dp
}

/**
 * 2. Data Class
 * Define la estructura de nuestros espaciados
 */
data class SpacingDataClass(
    val small: Dp,
    val medium: Dp,
    val large: Dp
)

/**
 * 3. Instancia Base
 * Contiene los valores iniciales
 */
val SpacingStyle = SpacingDataClass(
    small = DefaultSpacing.small,
    medium = DefaultSpacing.medium,
    large = DefaultSpacing.large
)

/**
 * 4. CompositionLocal
 * Define el LocalSpacing que usaremos en la UI
 */
val LocalSpacing = staticCompositionLocalOf { SpacingStyle }