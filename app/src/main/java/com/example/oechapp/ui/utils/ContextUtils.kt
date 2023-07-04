package com.example.oechapp.ui.utils

import android.content.Context
import android.content.pm.PackageManager
import android.util.TypedValue
import androidx.core.app.ActivityCompat

fun Context.themeRes(resource: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(resource, typedValue, true)
    return typedValue.data
}

fun Context.hasPermission(permission: String): Boolean {
    return ActivityCompat.checkSelfPermission(
        this,
        permission
    ) == PackageManager.PERMISSION_GRANTED
}