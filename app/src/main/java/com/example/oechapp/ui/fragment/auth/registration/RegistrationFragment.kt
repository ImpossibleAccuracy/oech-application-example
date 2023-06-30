package com.example.oechapp.ui.fragment.auth.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.oechapp.data.model.Resource
import com.example.oechapp.databinding.FragmentRegistrationBinding
import com.example.oechapp.databinding.LayoutSignupActionsBinding
import com.example.oechapp.ui.activity.routing.ApplicationRouter
import com.example.oechapp.ui.validator.EmailValidator
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RegistrationFragment : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding
    private lateinit var actionBinding: LayoutSignupActionsBinding

    private val viewModel: RegistrationViewModel by viewModels()

    @Inject
    lateinit var emailValidator: EmailValidator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        actionBinding = LayoutSignupActionsBinding.bind(binding.root)

        lifecycleScope.launch {
            viewModel.registrationResult
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
                    viewModel.handleRegistrationResult()
                }
        }

        actionBinding.signup.setOnClickListener {
            val email = binding.email.text?.toString() ?: ""
            val phone = binding.phone.text?.toString() ?: ""
            val fullName = binding.fullName.text?.toString() ?: ""
            val password = binding.password.text?.toString() ?: ""
            val isTermsAgreed = binding.agreeTerms.isChecked

            val emailError = emailValidator.validate(email)

            if (emailError == null) {
                binding.emailLayout.error = null
            } else {
                binding.emailLayout.error = getString(emailError)
                return@setOnClickListener
            }

            binding.agreeTerms.isErrorShown = !isTermsAgreed

            if (isTermsAgreed) {
                viewModel.signup(email, password, phone, fullName)
                actionBinding.signup.isEnabled = false
            }
        }

        actionBinding.signin.setOnClickListener {
            navigateSignIn()
        }

        return binding.root
    }

    private fun displayRegistrationError() {
        Snackbar.make(requireView(), "Error while sign up", Snackbar.LENGTH_SHORT).show()
    }

    private fun navigateSignIn() {
        val navController = findNavController()

        val direction = RegistrationFragmentDirections
            .actionNavRegistrationToNavLogin()

        navController.navigate(direction)
    }

    private fun navigateNext() {
        navigateSignIn()
    }
}