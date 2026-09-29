package com.example.monkmode.presentation.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monkmode.data.local.DailyCount
import com.example.monkmode.data.repository.HabitRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import java.text.SimpleDateFormat
import java.util.*

class StatsViewModel(
    private val repository: HabitRepository
) : ViewModel() {

    private val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    private val _startDate = MutableStateFlow(getStartOfWeek())
    val startDate: StateFlow<Calendar> = _startDate.asStateFlow()

    private val _endDate = MutableStateFlow(getEndOfWeek())
    val endDate: StateFlow<Calendar> = _endDate.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val completedHabitsInRange = combine(_startDate, _endDate) { start, end ->
        repository.getCompletedHabitsCountInRange(sdf.format(start.time), sdf.format(end.time))
    }.flatMapLatest { it }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    @OptIn(ExperimentalCoroutinesApi::class)
    val focusMinutesInRange = combine(_startDate, _endDate) { start, end ->
        repository.getTotalFocusMinutesInRange(sdf.format(start.time), sdf.format(end.time))
    }.flatMapLatest { it }.map { it ?: 0 }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    @OptIn(ExperimentalCoroutinesApi::class)
    val dailyFocusMinutes = combine(_startDate, _endDate) { start, end ->
        repository.getDailyFocusMinutes(sdf.format(start.time), sdf.format(end.time))
    }.flatMapLatest { it }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val totalHabits = repository.getTotalHabitsCount()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val bestStreak = repository.getBestStreak()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    fun nextWeek() {
        val newStart = (_startDate.value.clone() as Calendar).apply { add(Calendar.WEEK_OF_YEAR, 1) }
        val newEnd = (_endDate.value.clone() as Calendar).apply { add(Calendar.WEEK_OF_YEAR, 1) }
        _startDate.value = newStart
        _endDate.value = newEnd
    }

    fun previousWeek() {
        val newStart = (_startDate.value.clone() as Calendar).apply { add(Calendar.WEEK_OF_YEAR, -1) }
        val newEnd = (_endDate.value.clone() as Calendar).apply { add(Calendar.WEEK_OF_YEAR, -1) }
        _startDate.value = newStart
        _endDate.value = newEnd
    }

    private fun getStartOfWeek(): Calendar {
        return Calendar.getInstance().apply {
            set(Calendar.DAY_OF_WEEK, firstDayOfWeek)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
    }

    private fun getEndOfWeek(): Calendar {
        return (getStartOfWeek().clone() as Calendar).apply {
            add(Calendar.DAY_OF_WEEK, 6)
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
        }
    }
}
