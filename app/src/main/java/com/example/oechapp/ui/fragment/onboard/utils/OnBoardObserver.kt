package com.example.oechapp.ui.fragment.onboard.utils

enum class OnBoardAction {
    SKIP,
    SIGNIN,
    SIGNUP
}

interface OnBoardObserver {
    fun onOnBoardFinished(action: OnBoardAction)
}