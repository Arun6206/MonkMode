package com.example.monkmode.data.repository

import com.example.monkmode.data.local.FocusLogEntity
import com.example.monkmode.data.local.HabitDao
import com.example.monkmode.data.local.HabitEntity
import com.example.monkmode.data.local.HabitLogEntity

class HabitRepository(
    private val habitDao: HabitDao
) {

    fun getAllHabits() = habitDao.getAllHabits()

    fun getTotalHabitsCount() = habitDao.getTotalHabitsCount()

    fun getCompletedHabitsCount() = habitDao.getCompletedHabitsCount()

    fun getBestStreak() = habitDao.getBestStreak()

    suspend fun insertHabit(habit: HabitEntity) {
        habitDao.insertHabit(habit)
    }

    suspend fun updateHabit(habit: HabitEntity) {
        habitDao.updateHabit(habit)
    }

    suspend fun deleteHabit(habit: HabitEntity) {
        habitDao.deleteHabit(habit)
    }

    // Analytics Methods
    suspend fun logHabitCompletion(habitId: Int, date: String, completed: Boolean) {
        habitDao.insertHabitLog(HabitLogEntity(habitId = habitId, date = date, completed = completed))
    }

    fun getCompletedHabitsCountInRange(startDate: String, endDate: String) =
        habitDao.getCompletedHabitsCountInRange(startDate, endDate)

    fun getDailyCompletionCounts(startDate: String, endDate: String) =
        habitDao.getDailyCompletionCounts(startDate, endDate)

    suspend fun logFocusSession(date: String, minutes: Int) {
        habitDao.insertFocusLog(FocusLogEntity(date = date, minutes = minutes))
    }

    fun getTotalFocusMinutesInRange(startDate: String, endDate: String) =
        habitDao.getTotalFocusMinutesInRange(startDate, endDate)

    fun getDailyFocusMinutes(startDate: String, endDate: String) =
        habitDao.getDailyFocusMinutes(startDate, endDate)
}
