package com.example.monkmode.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.monkmode.presentation.login.LoginScreen
import com.example.monkmode.presentation.login.SignUpScreen
import com.example.monkmode.presentation.main.MainScreen
import com.example.monkmode.presentation.splash.SplashScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(navController = navController)
        }

        composable(Screen.Login.route) {
            LoginScreen(navController = navController)
        }

        composable(Screen.SignUp.route) {
            SignUpScreen(navController = navController)
        }

        // MainScreen loads up the bottom bar ecosystem
        composable(Screen.Main.route) {
            MainScreen(rootNavController = navController)
        }
    }
}