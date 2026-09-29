package com.example.monkmode.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ShowChart
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.monkmode.navigation.Screen
import com.example.monkmode.ui.theme.AppColors

@Composable
fun BottomNavBar(
    navController: NavController
) {
    val items = listOf(
        Screen.Home,
        Screen.Focus,
        Screen.Stats,
        Screen.AI,
        Screen.Profile
    )

    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    NavigationBar(
        modifier = Modifier
            .background(AppColors.Obsidian)
            .drawBehind {
                drawLine(
                    color = AppColors.Border.copy(alpha = 0.6f),
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = 1.dp.toPx()
                )
            },
        containerColor = AppColors.Obsidian,
        windowInsets = WindowInsets.navigationBars,
        tonalElevation = 0.dp
    ) {
        items.forEach { screen ->
            val baseRoute = currentRoute?.substringBefore('?')
            val isSelected = baseRoute == screen.route

            val animatedIconColor by animateColorAsState(
                targetValue = if (isSelected) AppColors.RoyalViolet else AppColors.SlateGray,
                animationSpec = tween(durationMillis = 250),
                label = "NavIconColor"
            )

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    val iconVector = when (screen) {
                        Screen.Home -> Icons.Rounded.Home
                        Screen.Focus -> Icons.Rounded.Timer
                        Screen.Stats -> Icons.AutoMirrored.Rounded.ShowChart
                        Screen.AI -> Icons.Rounded.AutoAwesome
                        Screen.Profile -> Icons.Rounded.Person
                        else -> Icons.Rounded.Home
                    }

                    Icon(
                        imageVector = iconVector,
                        contentDescription = null,
                        modifier = Modifier.size(22.dp),
                        tint = animatedIconColor
                    )
                },
                label = {
                    val labelText = when (screen) {
                        Screen.Home -> "Home"
                        Screen.Focus -> "Focus"
                        Screen.Stats -> "Analytics"
                        Screen.AI -> "Monk AI"
                        Screen.Profile -> "Profile"
                        else -> screen.route
                    }

                    Text(
                        text = labelText,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
                        letterSpacing = 0.2.sp
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = AppColors.RoyalViolet,
                    selectedTextColor = AppColors.RoyalViolet,
                    unselectedIconColor = AppColors.SlateGray,
                    unselectedTextColor = AppColors.SlateGray,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}
