package com.example.monkmode

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.monkmode.navigation.AppNavigation
import com.example.monkmode.notifications.NotificationHelper
import com.example.monkmode.notifications.NotificationScheduler
import com.example.monkmode.ui.theme.MonkModeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        
        // Initialize Notifications
        NotificationHelper.createNotificationChannel(this)
        NotificationScheduler.scheduleDailyReminder(this)
        
        setContent {
            MonkModeTheme {
                AppNavigation()
            }
        }
    }
}
