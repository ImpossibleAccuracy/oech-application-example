package com.example.oechapp.ui.fragment.profile

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class ProfileOption(
    @DrawableRes val icon: Int,
    @StringRes val title: Int,
    @StringRes val description: Int,
    val action: () -> Unit
)
