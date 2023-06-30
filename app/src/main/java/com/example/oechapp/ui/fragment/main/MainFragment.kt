package com.example.oechapp.ui.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.oechapp.R
import com.example.oechapp.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding

    private val viewModel: MainFragmentViewModel by viewModels()

    private val actions = listOf(
        MainAction(
            R.drawable.ic_call_centre,
            R.string.title_onboard_1,
            R.string.desc_onboard_1
        ) {

        },
        MainAction(
            R.drawable.ic_call_centre,
            R.string.title_onboard_1,
            R.string.desc_onboard_1
        ) {

        },
        MainAction(
            R.drawable.ic_call_centre,
            R.string.title_onboard_1,
            R.string.desc_onboard_1
        ) {

        },
        MainAction(
            R.drawable.ic_call_centre,
            R.string.title_onboard_1,
            R.string.desc_onboard_1
        ) {

        },
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        lifecycleScope.launch {
            viewModel.uiState
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .map { it.accountName }
                .filterNotNull()
                .collect {
                    binding.profile.greetings.text = getString(R.string.title_greeting, it)
                }
        }

        binding.actions.adapter = MainActionsAdapter(
            requireContext(), actions
        )

        binding.actions.setOnItemClickListener { _, _, position, _ ->
            actions[position].action.invoke()
        }

        return binding.root
    }
}