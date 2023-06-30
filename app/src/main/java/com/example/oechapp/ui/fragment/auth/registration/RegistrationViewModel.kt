package com.example.oechapp.ui.fragment.auth.registration

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
class RegistrationViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {
    private val _registrationResult = MutableStateFlow<Resource<Boolean>?>(null)
    val registrationResult = _registrationResult.asStateFlow()

    fun signup(email: String, password: String, phone: String, fullName: String) {
        viewModelScope.launch {
            val isSuccess = accountRepository.register(email, password, phone, fullName)

            _registrationResult.value = isSuccess
        }
    }

    fun handleRegistrationResult() {
        _registrationResult.value = null
    }
}