package com.example.oechapp.ui.fragment.onboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oechapp.databinding.FragmentOnboardFinishBinding
import com.example.oechapp.databinding.LayoutSignupActionsBinding
import com.example.oechapp.ui.fragment.onboard.utils.OnBoardAction
import com.example.oechapp.ui.fragment.onboard.utils.OnBoardObserver
import com.example.oechapp.ui.fragment.onboard.utils.OnBoardStore
import com.example.oechapp.ui.fragment.onboard.utils.PaginationAdapter
import com.example.oechapp.ui.utils.MarginItemDecoration
import com.example.oechapp.ui.utils.dp

class OnBoardThirdItemFragment : Fragment() {
    companion object {
        private const val ACTIVE_PAGE_INDEX = 2
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentOnboardFinishBinding.inflate(inflater, container, false)
        val controlsBinging = LayoutSignupActionsBinding.bind(binding.root)

        val item = OnBoardStore.items[ACTIVE_PAGE_INDEX]

        binding.content.item = item

        binding.content.dots.apply {
            adapter =
                PaginationAdapter(requireContext(), OnBoardStore.items.size, ACTIVE_PAGE_INDEX)
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            addItemDecoration(MarginItemDecoration(12.dp))
        }

        controlsBinging.signup.setOnClickListener {
            val activity = requireActivity()

            if (activity is OnBoardObserver) {
                activity.onOnBoardFinished(OnBoardAction.SIGNUP)
            }
        }

        controlsBinging.signin.setOnClickListener {
            val activity = requireActivity()

            if (activity is OnBoardObserver) {
                activity.onOnBoardFinished(OnBoardAction.SIGNIN)
            }
        }

        return binding.root
    }
}