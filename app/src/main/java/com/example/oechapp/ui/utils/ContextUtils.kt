package com.example.oechapp.ui.utils

import android.content.Context
import android.util.TypedValue

fun Context.themeRes(resource: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(resource, typedValue, true)
    return typedValue.data
}