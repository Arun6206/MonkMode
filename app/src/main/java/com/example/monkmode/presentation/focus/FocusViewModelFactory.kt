package com.example.monkmode.presentation.focus

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.monkmode.data.preferences.FocusPreferences

class FocusViewModelFactory(
    private val repository: com.example.monkmode.data.repository.HabitRepository,
    private val preferences: FocusPreferences
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FocusViewModel::class.java)) {
            return FocusViewModel(repository, preferences) as T
        }
        throw IllegalArgumentException("Unknown core ViewModel structure tracking reference requested")
    }
}