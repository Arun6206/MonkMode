package com.example.monkmode.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.monkmode.data.local.DatabaseProvider
import com.example.monkmode.data.repository.HabitRepository
import com.example.monkmode.presentation.components.*
import com.example.monkmode.presentation.focus.FocusViewModel
import com.example.monkmode.ui.theme.AppColors

@Composable
fun HomeScreen(
    navController: androidx.navigation.NavController,
    focusViewModel: FocusViewModel,
    onTimerClick: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var habitName by remember { mutableStateOf("") }

    val context = LocalContext.current
    val database = DatabaseProvider.getDatabase(context)
    val repository = HabitRepository(database.habitDao())

    val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModelFactory(repository))
    val habits by homeViewModel.habits.collectAsState()
    val bestStreak by homeViewModel.bestStreak.collectAsState()
    val timeLeft by focusViewModel.timeLeft.collectAsState()
    val isRunning by focusViewModel.isRunning.collectAsState()
    val focusMinutes by focusViewModel.focusMinutes.collectAsState()

    val completedHabits = habits.count { it.completed }
    val formattedTime = String.format("%02d:%02d", timeLeft / 60, timeLeft % 60)

    Box(modifier = Modifier.fillMaxSize()) {
        // Background Aesthetic
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF1A1A2E).copy(alpha = 0.4f),
                            AppColors.Obsidian
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            PremiumTopBar(
                onNotificationClick = {
                    com.example.monkmode.notifications.NotificationHelper.showNotification(
                        context,
                        "Monk Mode Active",
                        "Your discipline is your power. Keep going."
                    )
                }
            )

            HeroBanner(
                userName = "Arun",
                completedHabits = completedHabits,
                totalHabits = habits.size,
                focusMinutes = focusMinutes,
                streakCount = bestStreak ?: 0
            )

            Spacer(modifier = Modifier.height(32.dp))

            FocusTimerCard(
                time = formattedTime,
                isRunning = isRunning,
                onClick = onTimerClick
            )

            Spacer(modifier = Modifier.height(32.dp))

            SectionTitle(
                title = "Today's Habits",
                subtitle = "",
                actionText = "See All",
                onActionClick = {
                    navController.navigate("habits")
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                habits.forEach { habit ->
                    HabitTile(
                        habit = habit,
                        onCheckedChange = { homeViewModel.toggleHabit(habit) },
                        onDelete = { homeViewModel.deleteHabit(habit) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(120.dp)) // Extra space for FAB and Bottom Nav
        }

        // FAB Positioned manually since we removed Scaffold
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 100.dp, end = 24.dp)
        ) {
            PremiumFAB(onClick = { showDialog = true })
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                containerColor = AppColors.Surface,
                shape = RoundedCornerShape(24.dp),
                title = {
                    Text("Initialize Habit", color = Color.White, fontWeight = FontWeight.Bold)
                },
                text = {
                    OutlinedTextField(
                        value = habitName,
                        onValueChange = { habitName = it },
                        modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = AppColors.Obsidian,
                            unfocusedContainerColor = AppColors.Obsidian,
                            focusedBorderColor = AppColors.RoyalViolet,
                            unfocusedBorderColor = AppColors.Border,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        label = { Text("Habit Name") },
                        singleLine = true
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (habitName.isNotBlank()) {
                                homeViewModel.addHabit(habitName)
                                habitName = ""
                                showDialog = false
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = AppColors.RoyalViolet)
                    ) {
                        Text("Construct", color = Color.White)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Cancel", color = AppColors.SlateGray)
                    }
                }
            )
        }
    }
}
