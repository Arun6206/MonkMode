package com.example.monkmode.presentation.stats

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.rounded.ShowChart
import androidx.compose.material.icons.automirrored.rounded.TrendingUp
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.monkmode.data.local.DatabaseProvider
import com.example.monkmode.data.repository.HabitRepository
import com.example.monkmode.presentation.achievements.Achievement
import com.example.monkmode.presentation.achievements.AchievementCard
import com.example.monkmode.ui.theme.AppColors
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun StatsScreen(
    navController: androidx.navigation.NavController,
    fromProfile: Boolean = false
) {
    val context = LocalContext.current
    val database = DatabaseProvider.getDatabase(context)
    val repository = HabitRepository(database.habitDao())

    val statsViewModel: StatsViewModel = viewModel(
        factory = StatsViewModelFactory(repository)
    )

    val navigateBackToProfile = {
        navController.navigate(com.example.monkmode.navigation.Screen.Profile.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    // Handle back button / gesture safely
    BackHandler {
        if (fromProfile) {
            navigateBackToProfile()
        } else if (navController.previousBackStackEntry != null) {
            navController.popBackStack()
        }
    }

    val totalHabits by statsViewModel.totalHabits.collectAsState()
    val completedHabits by statsViewModel.completedHabitsInRange.collectAsState()
    val focusMinutes by statsViewModel.focusMinutesInRange.collectAsState()
    val bestStreak by statsViewModel.bestStreak.collectAsState()
    val dailyFocus by statsViewModel.dailyFocusMinutes.collectAsState()
    val startDate by statsViewModel.startDate.collectAsState()
    val endDate by statsViewModel.endDate.collectAsState()

    var selectedPeriod by remember { mutableIntStateOf(0) } // 0: Week, 1: Month, 2: Year

    val dateFormat = SimpleDateFormat("MMM d", Locale.getDefault())
    val dateRangeText = "${dateFormat.format(startDate.time)} - ${dateFormat.format(endDate.time)}"

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

        // Header Section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (fromProfile || navController.previousBackStackEntry != null) {
                IconButton(onClick = {
                    if (fromProfile) {
                        navigateBackToProfile()
                    } else {
                        navController.popBackStack()
                    }
                }) {
                    Icon(Icons.AutoMirrored.Rounded.ArrowBack, null, tint = Color.White)
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
            Column {
                Text(
                    text = "Your Progress",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Black
                )
                Text(
                    text = "Consistency compounds.",
                    color = AppColors.TextSecondary,
                    fontSize = 14.sp
                )
            }
        }

        // Date Range Selector
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { statsViewModel.previousWeek() }) {
                Icon(Icons.AutoMirrored.Rounded.KeyboardArrowLeft, null, tint = Color.White)
            }
            Text(
                text = dateRangeText,
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            IconButton(onClick = { statsViewModel.nextWeek() }) {
                Icon(Icons.AutoMirrored.Rounded.KeyboardArrowRight, null, tint = Color.White)
            }
        }

        // Period Tabs
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(AppColors.Surface)
                .padding(6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("Week", "Month", "Year").forEachIndexed { index, label ->
                val isActive = selectedPeriod == index
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isActive) AppColors.RoyalViolet else Color.Transparent)
                        .clickable { selectedPeriod = index }
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = label,
                        color = if (isActive) Color.White else AppColors.TextSecondary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Metrics Grid
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                MetricCard(
                    title = "Focus Time",
                    value = "${focusMinutes / 60}h ${focusMinutes % 60}m",
                    trend = "↑ 0%",
                    icon = Icons.Rounded.Schedule,
                    iconColor = AppColors.RoyalViolet,
                    modifier = Modifier.weight(1f)
                )
                MetricCard(
                    title = "Habits Completed",
                    value = "$completedHabits",
                    trend = "↑ 0%",
                    icon = Icons.Rounded.CheckCircle,
                    iconColor = AppColors.CyberEmerald,
                    modifier = Modifier.weight(1f)
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                MetricCard(
                    title = "Best Streak",
                    value = "${bestStreak ?: 0} days",
                    trend = "↑ 0%",
                    icon = Icons.Rounded.LocalFireDepartment,
                    iconColor = AppColors.IgniteOrange,
                    modifier = Modifier.weight(1f)
                )
                MetricCard(
                    title = "Completion Rate",
                    value = "${if (totalHabits > 0) (completedHabits * 100) / (totalHabits * 7) else 0}%",
                    trend = "↑ 0%",
                    icon = Icons.Rounded.BarChart,
                    iconColor = AppColors.CobaltBlue,
                    isGraphType = true,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Focus Rhythm Chart Section
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = AppColors.CardBg),
            border = androidx.compose.foundation.BorderStroke(1.dp, AppColors.Border)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column {
                        Text("Focus Rhythm", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Text("Your focus time throughout the week.", color = AppColors.TextSecondary, fontSize = 12.sp)
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(AppColors.Surface)
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Focus Time", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            Icon(Icons.Rounded.KeyboardArrowDown, null, tint = Color.White, modifier = Modifier.size(14.dp))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Bar Chart
                Row(
                    modifier = Modifier.fillMaxWidth().height(160.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    val days = listOf("M", "T", "W", "T", "F", "S", "S")
                    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    
                    days.forEachIndexed { i, day ->
                        // Calculate date for this day of week relative to startDate
                        val dayCal = (startDate.clone() as Calendar).apply {
                            add(Calendar.DAY_OF_WEEK, i)
                        }
                        val dateStr = sdf.format(dayCal.time)
                        val valMins = dailyFocus.find { it.date == dateStr }?.count ?: 0
                        
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("${valMins}m", color = AppColors.TextSecondary, fontSize = 9.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            Box(
                                modifier = Modifier
                                    .width(14.dp)
                                    .height((valMins.coerceAtMost(120) * 1.2).dp)
                                    .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                                    .background(
                                        brush = Brush.verticalGradient(
                                            colors = listOf(AppColors.VioletGradientStart, AppColors.VioletGradientEnd)
                                        )
                                    )
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(day, color = AppColors.TextSecondary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Insights Section
        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Insights", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                TextButton(onClick = { navController.navigate(com.example.monkmode.navigation.Screen.Insights.route) }) {
                    Text("See All", color = AppColors.RoyalViolet, fontSize = 13.sp)
                }
            }
            Text("Based on your activity this week.", color = AppColors.TextSecondary, fontSize = 12.sp)

            Spacer(modifier = Modifier.height(16.dp))

            InsightRow(Icons.Rounded.WbSunny, "You're strongest in the morning.", "Your focus sessions are usually longer before 12 PM.")
            InsightRow(Icons.AutoMirrored.Rounded.TrendingUp, "Your focus sessions increased by 32% this week.", "Great progress! Keep it up.")
            InsightRow(Icons.Rounded.CalendarToday, "You missed habits mostly on weekends.", "Try setting reminders for Saturday and Sunday.")
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Achievements Summary
        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
             Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Achievements", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                TextButton(onClick = { navController.navigate(com.example.monkmode.navigation.Screen.Achievements.route) }) {
                    Text("See All", color = AppColors.RoyalViolet, fontSize = 13.sp)
                }
            }
            Text("Small wins. A bigger you.", color = AppColors.TextSecondary, fontSize = 12.sp)
            
            Spacer(modifier = Modifier.height(16.dp))
            
            AchievementCard(
                Achievement(
                    title = "First Habit", 
                    description = "Task successfully conquered",
                    requirement = "Complete 1 habit",
                    unlocked = totalHabits >= 1
                )
            )
        }

        Spacer(modifier = Modifier.height(100.dp))
    }
}

@Composable
fun MetricCard(
    title: String,
    value: String,
    trend: String,
    icon: ImageVector,
    iconColor: Color,
    isGraphType: Boolean = false,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(120.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.CardBg),
        border = androidx.compose.foundation.BorderStroke(1.dp, AppColors.Border)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(iconColor.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(icon, null, tint = iconColor, modifier = Modifier.size(16.dp))
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(title, color = AppColors.TextSecondary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column {
                    Text(value, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Black)
                    Text(trend, color = AppColors.CyberEmerald, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
                
                // Mini Chart Indicator
                Box(modifier = Modifier.size(40.dp, 30.dp), contentAlignment = Alignment.BottomEnd) {
                    if (isGraphType) {
                        // Line chart mock
                        Icon(Icons.AutoMirrored.Rounded.ShowChart, null, tint = AppColors.RoyalViolet, modifier = Modifier.fillMaxSize())
                    } else {
                        // Bar chart mock
                        Row(horizontalArrangement = Arrangement.spacedBy(2.dp), verticalAlignment = Alignment.Bottom) {
                            repeat(4) { i ->
                                Box(
                                    modifier = Modifier
                                        .width(4.dp)
                                        .height((10 + (i * 5)).dp)
                                        .clip(CircleShape)
                                        .background(iconColor.copy(alpha = 0.6f))
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InsightRow(icon: ImageVector, title: String, subtitle: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, tint = Color(0xFFFBBF24), modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Text(subtitle, color = AppColors.TextSecondary, fontSize = 12.sp)
        }
        Icon(Icons.Rounded.ChevronRight, null, tint = AppColors.Border)
    }
}
