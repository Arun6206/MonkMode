package com.example.monkmode.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.monkmode.data.repository.HabitRepository

class HomeViewModelFactory(
    private val repository: HabitRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}