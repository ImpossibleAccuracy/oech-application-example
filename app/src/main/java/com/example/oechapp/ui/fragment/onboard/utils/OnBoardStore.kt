package com.example.oechapp.ui.fragment.onboard.utils

import com.example.oechapp.R
import com.example.oechapp.data.model.OnBoardItem

object OnBoardStore {
    val items = listOf(
        OnBoardItem(
            R.string.title_onboard_1,
            R.string.desc_onboard_1,
            R.drawable.drawable_onboard_1
        ),
        OnBoardItem(
            R.string.title_onboard_2,
            R.string.desc_onboard_2,
            R.drawable.drawable_onboard_2
        ),
        OnBoardItem(
            R.string.title_onboard_3,
            R.string.desc_onboard_3,
            R.drawable.drawable_onboard_3
        )
    )
}