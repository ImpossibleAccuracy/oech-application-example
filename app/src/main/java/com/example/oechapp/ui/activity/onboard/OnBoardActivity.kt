package com.example.oechapp.ui.activity.onboard

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import com.example.oechapp.R
import com.example.oechapp.store.ApplicationDataStore
import com.example.oechapp.store.dataStore
import com.example.oechapp.ui.activity.auth.AuthActivity
import com.example.oechapp.ui.fragment.onboard.utils.OnBoardAction
import com.example.oechapp.ui.fragment.onboard.utils.OnBoardObserver
import kotlinx.coroutines.launch


class OnBoardActivity : AppCompatActivity(), OnBoardObserver {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboard)
    }

    override fun onOnBoardFinished(action: OnBoardAction) {
        lifecycleScope.launch {
            dataStore.edit {
                it[ApplicationDataStore.IS_ONBOARD_VISITED] = true
            }

            when (action) {
                OnBoardAction.SIGNIN -> {
                    Intent(this@OnBoardActivity, AuthActivity::class.java).also {
                        it.putExtra(AuthActivity.ACTION_KEY, AuthActivity.LOGIN_ACTION)
                        startActivity(it)
                    }
                }
                else -> {
                    Intent(this@OnBoardActivity, AuthActivity::class.java).also {
                        it.putExtra(AuthActivity.ACTION_KEY, AuthActivity.REGISTRATION_ACTION)
                        startActivity(it)
                    }
                }
            }

            finish()
        }
    }
}