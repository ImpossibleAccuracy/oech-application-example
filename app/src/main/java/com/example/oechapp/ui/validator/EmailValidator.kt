package com.example.oechapp.ui.validator

import com.example.oechapp.R
import javax.inject.Inject

class EmailValidator @Inject constructor() {
    fun validate(email: String): Int? =
        when {
            email.isBlank() -> R.string.error_field_required
            !email.all { it.isLetterOrDigit() && it.isLowerCase() || it == '@' || it == '.' } || email.count { it == '.' } != 1 || email.count { it == '@' } != 1 -> R.string.error_email_invalid
            else -> null
        }
}