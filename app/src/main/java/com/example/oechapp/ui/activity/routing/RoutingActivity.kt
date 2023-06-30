package com.example.oechapp.ui.activity.routing

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.example.oechapp.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RoutingActivity : AppCompatActivity() {
    @Inject
    lateinit var applicationRouter: ApplicationRouter

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        route()
    }

    private fun route() {
        lifecycleScope.launch {
            applicationRouter.route(lifecycle)
            finish()

            overridePendingTransition(
                R.anim.splash_anim,
                com.google.android.material.R.anim.abc_slide_out_bottom
            )
        }
    }
}