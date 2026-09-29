package com.example.monkmode.presentation.ai

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

class AiViewModel : ViewModel() {

    val messages: SnapshotStateList<ChatMessage> = mutableStateListOf()

    var currentText by mutableStateOf("")
        private set

    var isTyping by mutableStateOf(false)
        private set

    init {
        messages.add(
            ChatMessage(
                text = "Greetings, **Operator**. I am the **Monk AI Core**.\n\n" +
                        "The AI coaching system is currently under maintenance. " +
                        "The remaining MonkMode features are fully available.",
                isUser = false
            )
        )
    }

    fun onTextChange(value: String) {
        currentText = value
    }

    fun sendQuickPrompt(promptText: String) {
        executeAiLifecycle(promptText)
    }

    fun sendMessage() {
        if (currentText.isBlank()) return

        val textToProcess = currentText
        currentText = ""

        executeAiLifecycle(textToProcess)
    }

    private fun executeAiLifecycle(userQuery: String) {

        // Add user's message
        messages.add(
            ChatMessage(
                text = userQuery,
                isUser = true
            )
        )

        isTyping = true

        viewModelScope.launch {

            // Small delay to keep the existing typing experience
            delay(500)

            val response = """
                **Monk AI is temporarily unavailable.**
                
                The AI coaching system is currently being configured.
                
                You can continue using the other MonkMode features:
                
                • **Habit Tracking**
                • **Focus Timer**
                • **Daily Statistics**
                • **Achievements**
                • **Dopamine Tracking**
                • **Notifications**
                
                **AI Coach will be available in a future update.**
            """.trimIndent()

            isTyping = false

            val aiMessageIndex = messages.size

            messages.add(
                ChatMessage(
                    text = "",
                    isUser = false
                )
            )

            // Keep the existing word-by-word animation
            val accumulatedString = StringBuilder()
            val words = response.split(" ")

            for (word in words) {
                accumulatedString.append(word).append(" ")

                messages[aiMessageIndex] = ChatMessage(
                    text = accumulatedString.toString(),
                    isUser = false
                )

                delay(20)
            }
        }
    }
}