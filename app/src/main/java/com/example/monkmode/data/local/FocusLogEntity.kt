package com.example.monkmode.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "focus_logs")
data class FocusLogEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: String, // yyyy-MM-dd
    val minutes: Int
)
