package com.example.oechapp.ui.fragment.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class MainAction(
    @DrawableRes val icon: Int,
    @StringRes val title: Int,
    @StringRes val description: Int,
    val action: () -> Unit
)
