package com.example.oechapp.ui.fragment.profile

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.oechapp.R
import com.example.oechapp.databinding.FragmentProfileBinding
import com.example.oechapp.ui.activity.auth.AuthActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentProfileBinding.inflate(inflater, container, false)

        lifecycleScope.launch {
            viewModel.uiState
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect { state ->
                    binding.name.text = state.fullName

                    if (state.isBalanceVisible) {
                        binding.balance.text = "N${state.balance}"
                        binding.balanceVisibilityState.setImageResource(R.drawable.ic_eye_slash)
                    } else {
                        binding.balance.text = "###"
                        binding.balanceVisibilityState.setImageResource(R.drawable.ic_eye)
                    }
                }
        }

        binding.balanceVisibilityState.setOnClickListener {
            viewModel.toggleBalanceVisibility()
        }

        binding.darkMode.isChecked = isUsingNightModeResources()

        binding.darkMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked != isUsingNightModeResources()) {
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
                }
            }
        }

        binding.logout.setOnClickListener {
            viewModel.logout()

            navigateToLogin()
        }

        return binding.root
    }

    private fun isUsingNightModeResources(): Boolean {
        return when (resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> true
            Configuration.UI_MODE_NIGHT_NO -> false
            Configuration.UI_MODE_NIGHT_UNDEFINED -> false
            else -> false
        }
    }

    private fun navigateToLogin() {
        Intent(requireContext(), AuthActivity::class.java).also { intent ->
            intent.putExtra(AuthActivity.ACTION_KEY, AuthActivity.LOGIN_ACTION)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP and Intent.FLAG_ACTIVITY_CLEAR_TOP

            startActivity(intent)
            requireActivity().finish()
        }
    }
}
