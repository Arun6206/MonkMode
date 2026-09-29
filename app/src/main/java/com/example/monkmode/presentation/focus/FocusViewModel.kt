package com.example.monkmode.presentation.focus

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monkmode.data.preferences.FocusPreferences
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

enum class FocusMode(val label: String, val defaultMinutes: Int) {
    DEEP_WORK("Deep Work", 30), // By default, set to 30 min context boundaries
    SHORT_BREAK("Short Break", 5),
    LONG_BREAK("Long Break", 15)
}

class FocusViewModel(
    private val repository: com.example.monkmode.data.repository.HabitRepository,
    private val focusPreferences: FocusPreferences
) : ViewModel() {

    private val _focusMinutes = MutableStateFlow(focusPreferences.getFocusMinutes())
    val focusMinutes: StateFlow<Int> = _focusMinutes

    private val _currentMode = MutableStateFlow(FocusMode.DEEP_WORK)
    val currentMode: StateFlow<FocusMode> = _currentMode

    // Dynamic variable explicitly holding deep work tracking state timelines (Defaults to 30)
    private val _deepWorkDuration = MutableStateFlow(30)
    val deepWorkDuration: StateFlow<Int> = _deepWorkDuration

    private val _timeLeft = MutableStateFlow(30 * 60)
    val timeLeft: StateFlow<Int> = _timeLeft

    private val _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> = _isRunning

    private var timerCoroutineJob: Job? = null

    fun switchMode(targetMode: FocusMode) {
        if (_currentMode.value == targetMode) return

        stopTimer()
        _currentMode.value = targetMode

        val baseMinutes = if (targetMode == FocusMode.DEEP_WORK) _deepWorkDuration.value else targetMode.defaultMinutes
        _timeLeft.value = baseMinutes * 60
    }

    /**
     * Updates deep work parameters dynamically based on options picked in dropdown selection rows.
     */
    fun updateDeepWorkDuration(minutes: Int) {
        if (_isRunning.value) return // Prevent accidental duration change during session

        val sanitizedMins = minutes.coerceIn(1, 300)
        _deepWorkDuration.value = sanitizedMins

        if (_currentMode.value == FocusMode.DEEP_WORK) {
            _timeLeft.value = sanitizedMins * 60
        }
    }

    fun startTimer() {
        if (_isRunning.value) return
        _isRunning.value = true

        timerCoroutineJob = viewModelScope.launch {
            while (_timeLeft.value > 0 && _isRunning.value) {
                delay(1000)
                _timeLeft.value--
            }

            if (_timeLeft.value == 0) {
                if (_currentMode.value == FocusMode.DEEP_WORK) {
                    val updatedMinutes = _focusMinutes.value + _deepWorkDuration.value
                    _focusMinutes.value = updatedMinutes
                    focusPreferences.saveFocusMinutes(updatedMinutes)
                    
                    // Log for Analytics
                    val sdf = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
                    val currentDate = sdf.format(java.util.Date())
                    viewModelScope.launch {
                        repository.logFocusSession(currentDate, _deepWorkDuration.value)
                    }
                }
                resetTimer()
            }
            _isRunning.value = false
        }
    }

    fun stopTimer() {
        _isRunning.value = false
        timerCoroutineJob?.cancel()
    }

    fun resetTimer() {
        stopTimer()
        val currentActiveMinutes = if (_currentMode.value == FocusMode.DEEP_WORK) _deepWorkDuration.value else _currentMode.value.defaultMinutes
        _timeLeft.value = currentActiveMinutes * 60
    }

    fun addFiveMinutes() {
        _timeLeft.value += 5 * 60
    }
}