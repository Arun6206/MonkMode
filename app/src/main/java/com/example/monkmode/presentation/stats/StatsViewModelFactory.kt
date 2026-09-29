package com.example.monkmode.presentation.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.monkmode.data.repository.HabitRepository

class StatsViewModelFactory(
    private val repository: HabitRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        return StatsViewModel(
            repository
        ) as T
    }
}