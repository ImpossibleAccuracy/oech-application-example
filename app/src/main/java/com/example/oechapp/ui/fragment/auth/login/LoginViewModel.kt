package com.example.oechapp.ui.fragment.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oechapp.data.model.Resource
import com.example.oechapp.data.repository.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {
    private val _loginResult = MutableStateFlow<Resource<Boolean>?>(null)
    val loginResult = _loginResult.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val isSuccess = accountRepository.login(email, password)

            _loginResult.value = isSuccess
        }
    }

    fun handleLoginResult() {
        _loginResult.value = null
    }
}