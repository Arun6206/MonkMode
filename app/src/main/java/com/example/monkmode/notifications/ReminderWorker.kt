package com.example.monkmode.notifications

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class ReminderWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(
    context,
    params
) {

    override suspend fun doWork(): Result {
        val calendar = java.util.Calendar.getInstance()
        val hour = calendar.get(java.util.Calendar.HOUR_OF_DAY)
        val minute = calendar.get(java.util.Calendar.MINUTE)

        val (title, message) = when {
            hour in 5..8 -> {
                "🌅 Morning Protocol Initiated" to "You are intelligent and disciplined. The world is sleeping while you build your empire. Execute your first task now."
            }
            hour in 13..16 -> {
                "⚡ Peak Performance Check" to "Neural flow is at its peak. Don't waste this intelligence on distractions. Return to your deep work session."
            }
            hour in 21..23 -> {
                "🌙 Daily De-brief" to "Reflection is for the wise. Review your wins, plan your tomorrow, and rest like a warrior. You've earned it."
            }
            else -> {
                "🔥 Monk Mode Active" to "Stay focused. Stay disciplined. Your future self is depending on the work you do right now."
            }
        }

        NotificationHelper.showNotification(
            applicationContext,
            title,
            message
        )

        return Result.success()
    }
}