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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oechapp.R
import com.example.oechapp.databinding.FragmentProfileBinding
import com.example.oechapp.ui.activity.auth.AuthActivity
import com.example.oechapp.ui.activity.notification.NotificationsActivity
import com.example.oechapp.ui.activity.payment.AddPaymentMethodActivity
import com.example.oechapp.ui.utils.MarginItemDecoration
import com.example.oechapp.ui.utils.dp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private val viewModel: ProfileViewModel by viewModels()

    private val options = listOf(
        ProfileOption(
            R.drawable.ic_profile,
            R.string.action_open_edit_profile,
            R.string.desc_open_edit_profile,
        ) {

        },
        ProfileOption(
            R.drawable.ic_reports,
            R.string.action_open_reports,
            R.string.desc_open_reports,
        ) {

        },
        ProfileOption(
            R.drawable.ic_notification,
            R.string.action_open_notifications,
            R.string.desc_open_notifications,
        ) {
            val intent = Intent(requireContext(), NotificationsActivity::class.java)
            startActivity(intent)
        },
        ProfileOption(
            R.drawable.ic_profile,
            R.string.action_open_credit_and_bank,
            R.string.desc_open_credit_and_bank,
        ) {
            val intent = Intent(requireContext(), AddPaymentMethodActivity::class.java)
            startActivity(intent)
        },
        ProfileOption(
            R.drawable.ic_profile,
            R.string.action_open_referals,
            R.string.desc_open_referals,
        ) {

        },
        ProfileOption(
            R.drawable.ic_profile,
            R.string.action_open_about,
            R.string.desc_open_about,
        ) {

        },
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.toolbar.title = findNavController().currentDestination?.label

        binding.options.also {
            it.adapter = ProfileOptionsAdapter(requireContext(), options)
            it.layoutManager = LinearLayoutManager(requireContext())

            it.addItemDecoration(MarginItemDecoration(12.dp))
        }

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
