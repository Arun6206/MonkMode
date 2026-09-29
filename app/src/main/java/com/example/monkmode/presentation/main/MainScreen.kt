package com.example.monkmode.presentation.main

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.monkmode.data.preferences.FocusPreferences
import com.example.monkmode.navigation.Screen
import com.example.monkmode.presentation.ai.AiScreen
import com.example.monkmode.presentation.ai.AiViewModel
import com.example.monkmode.presentation.components.BottomNavBar
import com.example.monkmode.presentation.focus.FocusScreen
import com.example.monkmode.presentation.focus.FocusViewModel
import com.example.monkmode.presentation.focus.FocusViewModelFactory
import com.example.monkmode.presentation.achievements.AchievementsScreen
import com.example.monkmode.presentation.home.HabitsScreen
import com.example.monkmode.presentation.home.HomeScreen
import com.example.monkmode.presentation.profile.ProfileScreen
import com.example.monkmode.presentation.settings.SettingsScreen
import com.example.monkmode.presentation.stats.InsightsScreen
import com.example.monkmode.presentation.stats.StatsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    rootNavController: NavHostController,
) {
    val bottomNavController = rememberNavController()
    val context = androidx.compose.ui.platform.LocalContext.current
    
    // Create shared FocusViewModel scoped to MainScreen
    val database = com.example.monkmode.data.local.DatabaseProvider.getDatabase(context)
    val repository = com.example.monkmode.data.repository.HabitRepository(database.habitDao())
    val focusViewModel: FocusViewModel = viewModel(
        factory = FocusViewModelFactory(repository, FocusPreferences(context))
    )

    Scaffold(
        bottomBar = {
            BottomNavBar(navController = bottomNavController)
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { paddingValues ->
        NavHost(
            navController = bottomNavController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding())
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navController = bottomNavController,
                    focusViewModel = focusViewModel
                ) {
                    bottomNavController.navigate(Screen.Focus.route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }

            composable("habits") {
                HabitsScreen()
            }

            composable(Screen.Focus.route) {
                FocusScreen(
                    navController = bottomNavController,
                    viewModel = focusViewModel
                )
            }

            composable(
                route = "stats?fromProfile={fromProfile}",
                arguments = listOf(
                    androidx.navigation.navArgument("fromProfile") {
                        type = androidx.navigation.NavType.BoolType
                        defaultValue = false
                    }
                )
            ) { backStackEntry ->
                val fromProfile = backStackEntry.arguments?.getBoolean("fromProfile") ?: false
                StatsScreen(
                    navController = bottomNavController,
                    fromProfile = fromProfile
                )
            }

            composable(
                route = "settings?section={section}",
                arguments = listOf(
                    androidx.navigation.navArgument("section") {
                        type = androidx.navigation.NavType.StringType
                        nullable = true
                        defaultValue = null
                    }
                )
            ) { backStackEntry ->
                val section = backStackEntry.arguments?.getString("section")
                SettingsScreen(
                    navController = bottomNavController,
                    initialSection = section
                )
            }

            composable(Screen.Achievements.route) {
                AchievementsScreen(navController = bottomNavController)
            }

            composable(Screen.Insights.route) {
                InsightsScreen(navController = bottomNavController)
            }

            // 🌟 UPGRADED: AI route with Parent-scoped Persistence Logic
            composable(Screen.AI.route) { backStackEntry ->
                // Look up the backstack entry of your inner start destination (Screen.Home)
                val parentEntry = remember(backStackEntry) {
                    bottomNavController.getBackStackEntry(bottomNavController.graph.startDestinationId)
                }

                // Scope the AiViewModel to that parent entry so its lifespan is tied to MainScreen
                val persistentAiViewModel: AiViewModel = viewModel(viewModelStoreOwner = parentEntry)

                // Pass the cached instance down to your layout screen
                AiScreen(
                    navController = bottomNavController,
                    viewModel = persistentAiViewModel
                )
            }

            composable(Screen.Profile.route) {
                ProfileScreen(
                    navController = bottomNavController,
                    onLogout = {
                        rootNavController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Main.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }
    }
}