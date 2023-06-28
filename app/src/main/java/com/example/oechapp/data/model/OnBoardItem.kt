package com.example.oechapp.data.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class OnBoardItem(
    @StringRes val title: Int,
    @StringRes val description: Int,
    @DrawableRes val image: Int,
)
