package com.example.monkmode.notifications

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.Calendar
import java.util.concurrent.TimeUnit

object NotificationScheduler {

    fun scheduleDailyReminder(context: Context) {
        // Schedule for 6:00 AM
        scheduleAtTime(context, "morning_reminder", 6, 0)
        
        // Schedule for 2:30 PM (14:30)
        scheduleAtTime(context, "afternoon_reminder", 14, 30)
        
        // Schedule for 10:30 PM (22:30)
        scheduleAtTime(context, "night_reminder", 22, 30)
    }

    private fun scheduleAtTime(context: Context, tag: String, hour: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        val now = calendar.timeInMillis
        
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)
        
        if (calendar.timeInMillis <= now) {
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }
        
        val initialDelay = calendar.timeInMillis - now

        val workRequest = PeriodicWorkRequestBuilder<ReminderWorker>(24, TimeUnit.HOURS)
            .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
            .addTag(tag)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            tag,
            ExistingPeriodicWorkPolicy.UPDATE,
            workRequest
        )
    }
}
