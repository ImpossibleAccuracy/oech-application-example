package com.example.oechapp.ui.activity.routing

import android.content.Context
import android.content.Intent
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import com.example.oechapp.data.repository.AccountRepository
import com.example.oechapp.store.ApplicationDataStore
import com.example.oechapp.store.dataStore
import com.example.oechapp.ui.activity.auth.AuthActivity
import com.example.oechapp.ui.activity.main.MainActivity
import com.example.oechapp.ui.activity.onboard.OnBoardActivity
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ApplicationRouter @Inject constructor(
    @ActivityContext private val context: Context,
    private val accountRepository: AccountRepository
) {
    suspend fun route(lifecycle: Lifecycle) {
        val preferences = context.dataStore.data
            .flowWithLifecycle(lifecycle)
            .first()

        val intent = when {
            !isOnboardVisited(preferences) -> {
                Intent(context, OnBoardActivity::class.java)
            }
            !isUserLoggedIn() -> {
                Intent(context, AuthActivity::class.java).apply {
                    putExtra(AuthActivity.ACTION_KEY, AuthActivity.REGISTRATION_ACTION)
                }
            }
            else -> {
                Intent(context, MainActivity::class.java)
            }
        }

        context.startActivity(intent)
    }

    private suspend fun isUserLoggedIn() = accountRepository.isAuthenticated()

    private fun isOnboardVisited(preferences: Preferences): Boolean {
        return preferences[ApplicationDataStore.IS_ONBOARD_VISITED] == true
    }
}