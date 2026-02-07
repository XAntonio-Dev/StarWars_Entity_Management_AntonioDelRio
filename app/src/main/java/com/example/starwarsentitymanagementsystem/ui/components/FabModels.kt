package com.example.starwarsentitymanagementsystem.ui.components

import androidx.compose.ui.graphics.vector.ImageVector

data class BaseFabState(
    val isVisible: Boolean = false,
    val icon: ImageVector? = null,
    val contentDescription: String? = null,
    val onClick: () -> Unit = {}
)