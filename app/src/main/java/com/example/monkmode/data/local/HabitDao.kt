package com.example.monkmode.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {

    @Insert
    suspend fun insertHabit(habit: HabitEntity)

    @Update
    suspend fun updateHabit(habit: HabitEntity)

    @Delete
    suspend fun deleteHabit(habit: HabitEntity)

    @Query("SELECT * FROM habits")
    fun getAllHabits(): Flow<List<HabitEntity>>

    @Query("SELECT COUNT(*) FROM habits")
    fun getTotalHabitsCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM habits WHERE completed = 1")
    fun getCompletedHabitsCount(): Flow<Int>

    @Query("SELECT MAX(streakCount) FROM habits")
    fun getBestStreak(): Flow<Int?>

    // Habit Logs for Analytics
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabitLog(log: HabitLogEntity)

    @Query("SELECT COUNT(*) FROM habit_logs WHERE date BETWEEN :startDate AND :endDate AND completed = 1")
    fun getCompletedHabitsCountInRange(startDate: String, endDate: String): Flow<Int>

    @Query("SELECT date, COUNT(*) as count FROM habit_logs WHERE date BETWEEN :startDate AND :endDate AND completed = 1 GROUP BY date")
    fun getDailyCompletionCounts(startDate: String, endDate: String): Flow<List<DailyCount>>

    // Focus Logs for Analytics
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFocusLog(log: FocusLogEntity)

    @Query("SELECT SUM(minutes) FROM focus_logs WHERE date BETWEEN :startDate AND :endDate")
    fun getTotalFocusMinutesInRange(startDate: String, endDate: String): Flow<Int?>

    @Query("SELECT date, SUM(minutes) as count FROM focus_logs WHERE date BETWEEN :startDate AND :endDate GROUP BY date")
    fun getDailyFocusMinutes(startDate: String, endDate: String): Flow<List<DailyCount>>
}

data class DailyCount(
    val date: String,
    val count: Int
)
