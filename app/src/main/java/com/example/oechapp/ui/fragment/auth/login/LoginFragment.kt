package com.example.oechapp.ui.fragment.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.oechapp.R
import com.example.oechapp.data.model.Resource
import com.example.oechapp.databinding.FragmentLoginBinding
import com.example.oechapp.databinding.LayoutSignupActionsBinding
import com.example.oechapp.ui.activity.main.MainActivity
import com.example.oechapp.ui.validator.EmailValidator
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var actionBinding: LayoutSignupActionsBinding

    private val viewModel: LoginViewModel by viewModels()

    @Inject
    lateinit var emailValidator: EmailValidator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        actionBinding = LayoutSignupActionsBinding.bind(binding.root)

        actionBinding.signup.text = getString(R.string.action_sign_in)
        actionBinding.signin.text = getString(R.string.action_sign_up)

        lifecycleScope.launch {
            viewModel.loginResult
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .filterNotNull()
                .filter { it !is Resource.Loading }
                .collect {
                    when (it) {
                        is Resource.Success -> {
                            if (it.data) {
                                navigateNext()
                            } else {
                                displayRegistrationError()
                            }
                        }
                        is Resource.Error -> {
                            it.error.printStackTrace()
                            displayRegistrationError()
                        }
                        else -> {}
                    }

                    actionBinding.signup.isEnabled = true
                    viewModel.handleLoginResult()
                }
        }

        actionBinding.signup.setOnClickListener {
            val email = binding.email.text?.toString() ?: ""
            val password = binding.password.text?.toString() ?: ""

            val emailError = emailValidator.validate(email)

            if (emailError == null) {
                binding.emailLayout.error = null
            } else {
                binding.emailLayout.error = getString(emailError)
                return@setOnClickListener
            }

            viewModel.login(email, password)
            actionBinding.signup.isEnabled = false
        }

        actionBinding.signin.setOnClickListener {
            navigateSignUp()
        }

        return binding.root
    }

    private fun displayRegistrationError() {
        Snackbar.make(requireView(), "Error while sign in", Snackbar.LENGTH_SHORT).show()
    }

    private fun navigateSignUp() {
        val navController = findNavController()

        val direction = LoginFragmentDirections
            .actionNavLoginToNavRegistration()

        navController.navigate(direction)
    }

    private fun navigateNext() {
        startActivity(Intent(requireContext(), MainActivity::class.java))
        requireActivity().finish()
    }
}