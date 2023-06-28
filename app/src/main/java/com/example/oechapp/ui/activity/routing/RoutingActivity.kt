package com.example.oechapp.ui.activity.routing

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.example.oechapp.R
import kotlinx.coroutines.launch

class RoutingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        Handler(mainLooper).postDelayed({
            lifecycleScope.launch {
                routeToStartDestination()
            }
        }, 1500)
    }

    private suspend fun routeToStartDestination() {
        ApplicationRouting.route(this, lifecycle)
        finish()

        overridePendingTransition(
            R.anim.splash_anim,
            com.google.android.material.R.anim.abc_slide_out_bottom
        )
    }
}