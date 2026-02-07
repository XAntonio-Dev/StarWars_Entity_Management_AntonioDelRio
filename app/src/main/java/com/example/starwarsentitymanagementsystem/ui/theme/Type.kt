package com.example.starwarsentitymanagementsystem.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.starwarsentitymanagementsystem.R
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = starWarsYellow,
    background = colorBackground,
    onBackground = colorText,
    surface = colorBackground
)

@Composable
fun StarWarsTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

val starWarsFont = FontFamily(
    Font(R.font.star_wars_font, FontWeight.Normal)
)

val Typography = Typography(
    headlineLarge = TextStyle(
        fontFamily = starWarsFont,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        lineHeight = 42.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = starWarsFont,
        fontWeight = FontWeight.Medium,
        fontSize = 28.sp
    ),
    titleLarge = TextStyle(
        fontFamily = starWarsFont,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = starWarsFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    labelLarge = TextStyle(
        fontFamily = starWarsFont,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    )
)
