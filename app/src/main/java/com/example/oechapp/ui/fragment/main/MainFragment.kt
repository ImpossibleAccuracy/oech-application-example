package com.example.oechapp.ui.fragment.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.oechapp.R
import com.example.oechapp.databinding.FragmentMainBinding
import com.example.oechapp.ui.activity.send_package.SendPackageActivity
import com.example.oechapp.ui.utils.MarginItemDecoration
import com.example.oechapp.ui.utils.dp
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
            R.drawable.ic_reports,
            R.string.title_onboard_1,
            R.string.desc_onboard_1
        ) {
            val intent = Intent(requireContext(), SendPackageActivity::class.java)
            startActivity(intent)
        },
        MainAction(
            R.drawable.ic_notification,
            R.string.title_onboard_1,
            R.string.desc_onboard_1
        ) {

        },
        MainAction(
            R.drawable.ic_profile,
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

        binding.actions.let {
            it.adapter = MainActionsAdapter(
                requireContext(),
                actions
            )

            it.layoutManager = GridLayoutManager(requireContext(), 2)

            it.addItemDecoration(MarginItemDecoration(12.dp))
        }

        return binding.root
    }
}