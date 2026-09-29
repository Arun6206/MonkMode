package com.example.monkmode.data.preferences

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FocusPreferences(
    context: Context
) {
    private val prefs = context.getSharedPreferences(
        "focus_prefs",
        Context.MODE_PRIVATE
    )

    fun getFocusMinutesFlow(): Flow<Int> = callbackFlow {
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
            if (key == "focus_minutes") {
                trySend(sharedPreferences.getInt(key, 0))
            }
        }
        prefs.registerOnSharedPreferenceChangeListener(listener)
        trySend(prefs.getInt("focus_minutes", 0))
        awaitClose {
            prefs.unregisterOnSharedPreferenceChangeListener(listener)
        }
    }

    fun getFocusMinutes(): Int {
        return prefs.getInt("focus_minutes", 0)
    }

    fun saveFocusMinutes(minutes: Int) {
        prefs.edit()
            .putInt("focus_minutes", minutes)
            .apply()
    }
}
