package com.example.monkmode.navigation

sealed class Screen(
    val route: String
) {

    object Splash : Screen("splash")
    object Login : Screen("login")
    object SignUp : Screen("signup")
    object Main : Screen("main")
    object Home : Screen("home")
    object Focus : Screen("focus")
    object Stats : Screen("stats")
    object Profile : Screen("profile")
    object AI : Screen("ai")
    object Settings : Screen("settings")
    object Achievements : Screen("achievements")
    object Insights : Screen("insights")
}