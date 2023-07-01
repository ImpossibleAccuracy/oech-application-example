package com.example.oechapp.ui.fragment.profile

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oechapp.data.repository.AccountRepository
import com.example.oechapp.store.ApplicationDataStore.IS_BALANCE_VISIBLE
import com.example.oechapp.store.dataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val accountRepository: AccountRepository,
    private val authRepository: AccountRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val fullName = accountRepository.getAccountName()

            _uiState.update {
                it.copy(
                    fullName = fullName ?: "",
                )
            }
        }

        viewModelScope.launch {
            val balance = accountRepository.getAccountBalance()

            _uiState.update {
                it.copy(
                    balance = balance,
                )
            }
        }

        viewModelScope.launch {
            val isBalanceVisible = isBalanceVisible()

            _uiState.update {
                it.copy(
                    isBalanceVisible = isBalanceVisible
                )
            }
        }
    }

    fun toggleBalanceVisibility() {
        updateBalanceVisibility(!_uiState.value.isBalanceVisible)
    }

    fun updateBalanceVisibility(isVisible: Boolean) {
        _uiState.update {
            it.copy(isBalanceVisible = isVisible)
        }

        viewModelScope.launch {
            context.dataStore.edit {
                it[IS_BALANCE_VISIBLE] = isVisible
            }
        }
    }

    private suspend fun isBalanceVisible(): Boolean =
        this.context.dataStore.data.first().let {
            it[IS_BALANCE_VISIBLE] == true
        }

    fun logout() {
        viewModelScope.launch {
            accountRepository.logout()
        }
    }
}