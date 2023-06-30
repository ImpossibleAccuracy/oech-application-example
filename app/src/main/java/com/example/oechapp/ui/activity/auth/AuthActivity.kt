package com.example.oechapp.ui.activity.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.oechapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    companion object {
        const val ACTION_KEY = "AuthAction"
        const val LOGIN_ACTION = "Login"
        const val PASSWORD_RESTORE = "Login"
        const val REGISTRATION_ACTION = "Registration"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        val navHost = supportFragmentManager
            .findFragmentById(R.id.nav_host_auth) as NavHostFragment
        val navController = navHost.navController

        intent.getStringExtra(ACTION_KEY).let { action ->
            when (action) {
                REGISTRATION_ACTION -> showRegistration(navController)
                else -> showLogin(navController)
            }
        }
    }

    private fun showLogin(navController: NavController) {
        val inflater = navController.navInflater

        val graph = inflater.inflate(R.navigation.auth_nav_graph)
        graph.setStartDestination(R.id.nav_login)

        navController.setGraph(graph, null)
    }

    private fun showRegistration(navController: NavController) {
        val inflater = navController.navInflater

        val graph = inflater.inflate(R.navigation.auth_nav_graph)
        graph.setStartDestination(R.id.nav_registration)

        navController.setGraph(graph, null)
    }
}