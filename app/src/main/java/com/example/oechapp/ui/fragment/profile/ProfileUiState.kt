package com.example.oechapp.ui.fragment.profile

data class ProfileUiState(
    val isBalanceVisible: Boolean = false,
    val balance: Int? = null,
    val fullName: String = ""
)