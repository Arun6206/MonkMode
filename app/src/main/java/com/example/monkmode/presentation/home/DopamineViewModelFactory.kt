package com.example.monkmode.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.monkmode.data.preferences.FocusPreferences
import com.example.monkmode.data.repository.HabitRepository

class DopamineViewModelFactory(
    private val repository: HabitRepository,
    private val preferences: FocusPreferences
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        return DopamineViewModel(
            repository,
            preferences
        ) as T
    }
}