package com.example.monkmode.data.local

import android.content.Context
import androidx.room.Room

object DatabaseProvider {

    @Volatile
    private var INSTANCE: MonkDatabase? = null

    fun getDatabase(
        context: Context
    ): MonkDatabase {

        return INSTANCE ?: synchronized(this) {

            val instance = Room.databaseBuilder(
                context.applicationContext,
                MonkDatabase::class.java,
                "monk_database"
            )
            .fallbackToDestructiveMigration()
            .build()

            INSTANCE = instance

            instance
        }
    }
}