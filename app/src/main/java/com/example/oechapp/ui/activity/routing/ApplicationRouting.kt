package com.example.oechapp.ui.activity.routing

import android.content.Context
import android.content.Intent
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import com.example.oechapp.store.ApplicationDataStore
import com.example.oechapp.store.dataStore
import com.example.oechapp.ui.activity.main.MainActivity
import com.example.oechapp.ui.activity.onboard.OnBoardActivity
import kotlinx.coroutines.flow.first

object ApplicationRouting {
    suspend fun route(context: Context, lifecycle: Lifecycle) {
        val preferences = context.dataStore.data
            .flowWithLifecycle(lifecycle)
            .first()

        val intent = when {
            !isOnboardVisited(preferences) -> {
                Intent(context, OnBoardActivity::class.java)
            }
            else -> {
                Intent(context, MainActivity::class.java)
            }
        }

        context.startActivity(intent)
    }

    private fun isOnboardVisited(preferences: Preferences): Boolean {
        return preferences[ApplicationDataStore.IS_ONBOARD_VISITED] == true
    }
}