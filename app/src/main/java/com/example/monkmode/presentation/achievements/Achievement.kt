package com.example.monkmode.presentation.achievements

import androidx.compose.ui.graphics.vector.ImageVector

data class Achievement(
    val title: String,
    val description: String,
    val requirement: String,
    val icon: ImageVector? = null,
    val unlocked: Boolean = false,
    val progress: Float = 0f, // 0.0 to 1.0
    val level: Int = 1
)
