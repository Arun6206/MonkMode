package com.example.monkmode.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monkmode.data.local.HabitEntity
import com.example.monkmode.data.repository.HabitRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: HabitRepository
) : ViewModel() {

    // Clean, high-performance data stream exposing habits to your premium UI
    val habits = repository.getAllHabits()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Best streak across all habits
    val bestStreak = repository.getBestStreak()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    /**
     * Toggles a habit's completion status.
     * Contains highly refined streak logic to protect historical user effort from accidental unchecks.
     */
    fun toggleHabit(habit: HabitEntity) {
        viewModelScope.launch {
            val isNowCompleted = !habit.completed
            val updatedHabit = if (habit.completed) {
                habit.copy(
                    completed = false,
                    streakCount = maxOf(0, habit.streakCount - 1)
                )
            } else {
                habit.copy(
                    completed = true,
                    streakCount = habit.streakCount + 1
                )
            }

            repository.updateHabit(updatedHabit)
            
            // Log for Analytics
            val sdf = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
            val currentDate = sdf.format(java.util.Date())
            repository.logHabitCompletion(habit.id, currentDate, isNowCompleted)
        }
    }

    /**
     * Constructs a fresh habit entity into the storage engine.
     */
    fun addHabit(name: String) {
        if (name.isBlank()) return // Safety filter guarding database from empty junk rows

        viewModelScope.launch {
            repository.insertHabit(
                HabitEntity(
                    name = name.trim(),
                    completed = false,
                    streakCount = 0
                )
            )
        }
    }

    /**
     * Destroys a target habit safely out of persistence blocks.
     */
    fun deleteHabit(habit: HabitEntity) {
        viewModelScope.launch {
            repository.deleteHabit(habit)
        }
    }
}