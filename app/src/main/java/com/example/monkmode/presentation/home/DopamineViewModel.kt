package com.example.monkmode.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monkmode.data.preferences.FocusPreferences
import com.example.monkmode.data.repository.HabitRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class DopamineViewModel(
    repository: HabitRepository,
    focusPreferences: FocusPreferences
) : ViewModel() {

    private val focusMinutesFlow = focusPreferences.getFocusMinutesFlow()

    val dopamineScore = combine(
        repository.getCompletedHabitsCount(),
        repository.getTotalHabitsCount(),
        focusMinutesFlow
    ) { completed, _, focusMinutes ->
        // Logic: Base 100 + 10 points per habit + 1 point per 5 mins focus
        100 + (completed * 10) + (focusMinutes / 5)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 100
    )
}
