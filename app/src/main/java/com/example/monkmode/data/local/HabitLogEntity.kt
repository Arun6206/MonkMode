package com.example.monkmode.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habit_logs")
data class HabitLogEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val habitId: Int,
    val date: String, // ISO-8601 date (yyyy-MM-dd)
    val completed: Boolean
)
