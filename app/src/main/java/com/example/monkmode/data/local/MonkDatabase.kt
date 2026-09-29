package com.example.monkmode.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        HabitEntity::class,
        HabitLogEntity::class,
        FocusLogEntity::class
    ],
    version = 2
)
abstract class MonkDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao
}
