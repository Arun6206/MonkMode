package com.example.monkmode.presentation.home

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.monkmode.data.local.DatabaseProvider
import com.example.monkmode.data.repository.HabitRepository
import com.example.monkmode.presentation.components.HabitTile
import com.example.monkmode.ui.theme.AppColors

@Composable
fun HabitsScreen() {
    val context = androidx.compose.ui.platform.LocalContext.current
    val database = DatabaseProvider.getDatabase(context)
    val repository = HabitRepository(database.habitDao())
    val viewModel: HomeViewModel = viewModel(factory = HomeViewModelFactory(repository))
    
    val habits by viewModel.habits.collectAsState()
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        containerColor = AppColors.Obsidian,
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { _ ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 100.dp)
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Today's Habits",
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Black
                    )
                    Text(
                        text = "Small habits. Big changes.",
                        color = AppColors.TextSecondary,
                        fontSize = 14.sp
                    )
                }

                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(AppColors.RoyalViolet.copy(alpha = 0.1f))
                        .clickable { /* Add habit logic */ },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Rounded.Add, null, tint = AppColors.RoyalViolet, modifier = Modifier.size(24.dp))
                }
            }

            // Tab Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 12.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(AppColors.Surface)
                    .padding(6.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("Today", "This Week", "All Habits").forEachIndexed { index, label ->
                    val isActive = selectedTab == index
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isActive) AppColors.RoyalViolet else Color.Transparent)
                            .clickable { selectedTab = index }
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = label,
                            color = if (isActive) Color.White else AppColors.TextSecondary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Habit List
            Column(modifier = Modifier.fillMaxWidth()) {
                habits.forEach { habit ->
                    HabitTile(
                        habit = habit,
                        onCheckedChange = { viewModel.toggleHabit(habit) },
                        onDelete = { viewModel.deleteHabit(habit) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Large Motivational Image Card (No Quote overlay as requested)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .padding(horizontal = 24.dp),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = AppColors.SurfaceLight),
                border = androidx.compose.foundation.BorderStroke(1.dp, AppColors.Border)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    // Gradient Background
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(Color(0xFF1E1E2E), Color(0xFF07070B))
                                )
                            )
                    )
                    
                    // Subtle Tip row at bottom
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(24.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(AppColors.RoyalViolet.copy(alpha = 0.1f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Rounded.Lightbulb, null, tint = AppColors.RoyalViolet, modifier = Modifier.size(18.dp))
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text("Tip for Today", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            Text("Consistency beats intensity.", color = AppColors.TextSecondary, fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}
