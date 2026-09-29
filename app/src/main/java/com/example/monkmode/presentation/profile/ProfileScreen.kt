package com.example.monkmode.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.monkmode.presentation.login.LoginViewModel
import com.example.monkmode.ui.theme.AppColors

@Composable
fun ProfileScreen(
    navController: androidx.navigation.NavController,
    onLogout: () -> Unit
) {
    val loginViewModel: LoginViewModel = viewModel()
    val email = loginViewModel.getCurrentUserEmail() ?: "aarunyadav0610@gmail.com"

    val context = androidx.compose.ui.platform.LocalContext.current
    val database = com.example.monkmode.data.local.DatabaseProvider.getDatabase(context)
    val repository = com.example.monkmode.data.repository.HabitRepository(database.habitDao())
    val statsViewModel: com.example.monkmode.presentation.stats.StatsViewModel = viewModel(
        factory = com.example.monkmode.presentation.stats.StatsViewModelFactory(repository)
    )

    val completedHabits by statsViewModel.completedHabitsInRange.collectAsState()
    val bestStreak by statsViewModel.bestStreak.collectAsState()
    val focusMinutes by statsViewModel.focusMinutesInRange.collectAsState()

    val navigateToStats = {
        navController.navigate("stats?fromProfile=true") {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Obsidian)
            .verticalScroll(rememberScrollState())
    ) {
        // Distinct Status Bar Area
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .background(Color.Black.copy(alpha = 0.3f))
        )

        // Header Section with Profile Info
        ProfileHeader(
            userName = "Arun Kumar Yadav", 
            email = email,
            streak = bestStreak ?: 0,
            habitsDone = completedHabits,
            focusMinutes = focusMinutes,
            onNavigateToStats = navigateToStats
        )

        Spacer(modifier = Modifier.height(24.dp))

        // XP Section
        XPSection()

        Spacer(modifier = Modifier.height(32.dp))

        // Settings Categories
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            SettingsCategory(
                title = "My Account",
                items = listOf(
                    SettingItem("My Stats", Icons.Rounded.BarChart, AppColors.TextPrimary),
                    SettingItem("Achievements", Icons.Rounded.EmojiEvents, AppColors.TextPrimary),
                    SettingItem("Settings", Icons.Rounded.Settings, AppColors.TextPrimary),
                    SettingItem("Help & Support", Icons.Rounded.HelpOutline, AppColors.TextPrimary)
                ),
                navController = navController,
                onNavigateToStats = navigateToStats
            )

            SettingsCategory(
                title = "App Preferences",
                items = listOf(
                    SettingItem("Focus Settings", Icons.Rounded.Schedule, AppColors.TextPrimary),
                    SettingItem("Habit Settings", Icons.Rounded.SettingsSuggest, AppColors.TextPrimary),
                    SettingItem("Notifications & Reminders", Icons.Rounded.Notifications, AppColors.TextPrimary),
                    SettingItem("Appearance", Icons.Rounded.Palette, AppColors.TextPrimary)
                ),
                navController = navController
            )

            SettingsCategory(
                title = "Account & Privacy",
                items = listOf(
                    SettingItem("Privacy & Encryption", Icons.Rounded.Security, AppColors.TextPrimary),
                    SettingItem("Manage Account", Icons.Rounded.Person, AppColors.TextPrimary)
                ),
                navController = navController
            )

            // Logout Button
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { 
                        loginViewModel.logout()
                        onLogout()
                    },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1111)),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF331A1A))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Rounded.Logout, null, tint = AppColors.Error, modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("Logout Session", color = AppColors.Error, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(100.dp))
    }
}

@Composable
fun ProfileHeader(
    userName: String, 
    email: String,
    streak: Int = 0,
    focusMinutes: Int = 0,
    habitsDone: Int = 0,
    onNavigateToStats: (() -> Unit)? = null
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
    ) {
        // Background Gradient (Replacement for image)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFF1A1A2E).copy(alpha = 0.6f), AppColors.Obsidian)
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = userName,
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Black
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = email,
                            color = AppColors.TextSecondary,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(Icons.Rounded.Edit, null, tint = AppColors.SlateGray, modifier = Modifier.size(16.dp))
                    }
                }
                
                // Profile Image with Camera Icon
                Box(contentAlignment = Alignment.BottomEnd) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.White.copy(alpha = 0.1f), CircleShape)
                            .background(AppColors.SurfaceLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Rounded.Person, null, tint = Color.White.copy(alpha = 0.4f), modifier = Modifier.size(60.dp))
                    }
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(AppColors.RoyalViolet)
                            .border(2.dp, AppColors.Obsidian, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Rounded.PhotoCamera, null, tint = Color.White, modifier = Modifier.size(16.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "\"A better you is always in progress.\"",
                color = AppColors.TextSecondary,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Metrics Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ProfileMetric("$streak", "Day Streak", Icons.Rounded.LocalFireDepartment, AppColors.IgniteOrange, onClick = onNavigateToStats)
                ProfileMetric("${focusMinutes / 60}h ${focusMinutes % 60}m", "Focus Time", Icons.Rounded.Schedule, AppColors.RoyalViolet, onClick = onNavigateToStats)
                ProfileMetric("$habitsDone", "Habits Done", Icons.Rounded.CheckCircle, AppColors.CyberEmerald, onClick = onNavigateToStats)
                ProfileLevelMetric("Monk Level 3", "Consistent Seeker")
            }
        }
    }
}

@Composable
fun ProfileMetric(
    value: String, 
    label: String, 
    icon: ImageVector, 
    color: Color,
    onClick: (() -> Unit)? = null
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = if (onClick != null) {
            Modifier.clip(RoundedCornerShape(8.dp)).clickable { onClick() }
        } else {
            Modifier
        }
    ) {
        Icon(icon, null, tint = color, modifier = Modifier.size(20.dp))
        Text(value, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Black)
        Text(label, color = AppColors.TextSecondary, fontSize = 10.sp)
    }
}

@Composable
fun ProfileLevelMetric(level: String, status: String) {
    Column(horizontalAlignment = Alignment.End) {
        Icon(Icons.Rounded.SignalCellularAlt, null, tint = AppColors.RoyalViolet, modifier = Modifier.size(20.dp))
        Text(level, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Black)
        Text(status, color = AppColors.TextSecondary, fontSize = 10.sp)
    }
}

@Composable
fun XPSection() {
    Column(modifier = Modifier.padding(horizontal = 24.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color.White.copy(alpha = 0.05f))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.64f) // 320/500
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(4.dp))
                    .background(
                        brush = Brush.horizontalGradient(
                            listOf(AppColors.VioletGradientStart, AppColors.VioletGradientEnd)
                        )
                    )
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Text("320 / 500 XP", color = AppColors.TextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun SettingsCategory(
    title: String, 
    items: List<SettingItem>,
    navController: androidx.navigation.NavController,
    onNavigateToStats: (() -> Unit)? = null
) {
    Column {
        Text(
            text = title,
            color = AppColors.TextSecondary,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = AppColors.CardBg),
            border = androidx.compose.foundation.BorderStroke(1.dp, AppColors.Border)
        ) {
            Column {
                items.forEachIndexed { index, item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { 
                                when (item.label) {
                                    "My Stats" -> {
                                        if (onNavigateToStats != null) {
                                            onNavigateToStats()
                                        } else {
                                            navController.navigate("stats?fromProfile=true") {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                    }
                                    "Achievements" -> {
                                        navController.navigate(com.example.monkmode.navigation.Screen.Achievements.route) {
                                            launchSingleTop = true
                                        }
                                    }
                                    "Settings" -> {
                                        navController.navigate(com.example.monkmode.navigation.Screen.Settings.route) {
                                            launchSingleTop = true
                                        }
                                    }
                                    else -> {
                                        navController.navigate("settings?section=${item.label}") {
                                            launchSingleTop = true
                                        }
                                    }
                                }
                            }
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color.White.copy(alpha = 0.03f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(item.icon, null, tint = item.iconColor, modifier = Modifier.size(20.dp))
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = item.label,
                            color = Color.White,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.weight(1f)
                        )
                        Icon(Icons.Rounded.ChevronRight, null, tint = AppColors.SlateGray, modifier = Modifier.size(20.dp))
                    }
                    if (index < items.size - 1) {
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            thickness = 1.dp,
                            color = AppColors.Border.copy(alpha = 0.5f)
                        )
                    }
                }
            }
        }
    }
}

data class SettingItem(val label: String, val icon: ImageVector, val iconColor: Color)
