package com.example.monkmode.presentation.login

import androidx.lifecycle.ViewModel
import com.example.monkmode.data.auth.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed interface LoginUiState {
    object Idle : LoginUiState
    object Loading : LoginUiState
    object Success : LoginUiState
    data class Error(val message: String) : LoginUiState
}

class LoginViewModel : ViewModel() {

    private val repository = AuthRepository()

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _uiState.value = LoginUiState.Error("Email and password cannot be empty")
            return
        }

        _uiState.value = LoginUiState.Loading
        repository.login(email, password) { success, message ->
            if (success) {
                _uiState.value = LoginUiState.Success
            } else {
                _uiState.value = LoginUiState.Error(message ?: "An unknown error occurred")
            }
        }
    }

    fun resetState() {
        _uiState.value = LoginUiState.Idle
    }

    fun signUp(
        email: String,
        password: String,
        confirmPass: String
    ) {
        if (email.isBlank() || password.isBlank() || confirmPass.isBlank()) {
            _uiState.value = LoginUiState.Error("All fields are required")
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _uiState.value = LoginUiState.Error("Invalid email format")
            return
        }

        if (password.length < 8) {
            _uiState.value = LoginUiState.Error("Password must be at least 8 characters")
            return
        }

        if (!password.any { it.isUpperCase() } || !password.any { it.isDigit() } || !password.any { !it.isLetterOrDigit() }) {
            _uiState.value = LoginUiState.Error("Password must contain uppercase, number and symbol")
            return
        }

        if (password != confirmPass) {
            _uiState.value = LoginUiState.Error("Passwords do not match")
            return
        }

        _uiState.value = LoginUiState.Loading
        repository.signUp(email, password) { success, message ->
            if (success) {
                _uiState.value = LoginUiState.Success
            } else {
                _uiState.value = LoginUiState.Error(message ?: "Signup failed")
            }
        }
    }

    fun getCurrentUserEmail(): String = repository.getCurrentUserEmail()
    
    fun isUserLoggedIn(): Boolean = repository.isUserLoggedIn()

    fun logout() {
        repository.logout()
    }
}
