package com.example.oechapp.ui.fragment.send_package.enter

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oechapp.R
import com.example.oechapp.data.model.OrderAddressDetails
import com.example.oechapp.data.model.PackageDetails
import com.example.oechapp.databinding.FragmentEnterOrderDetailsBinding
import com.example.oechapp.databinding.ItemAddressDetailsInputsBinding
import com.example.oechapp.ui.fragment.send_package.SendPackageViewModel
import com.example.oechapp.ui.utils.MarginItemDecoration
import com.example.oechapp.ui.utils.dp
import com.example.oechapp.ui.utils.hasPermission
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EnterOrderDetailsFragment : Fragment() {
    private lateinit var binding: FragmentEnterOrderDetailsBinding

    private val viewModel: SendPackageViewModel by navGraphViewModels(R.id.send_package_nav_graph) {
        defaultViewModelProviderFactory
    }

    private val requestPermissionLauncher by lazy {
        requireActivity().registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                viewModel.loadOriginByDeviceLocation()
            }
        }
    }

    private lateinit var adapter: DestinationsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = DestinationsAdapter(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEnterOrderDetailsBinding.inflate(inflater, container, false)

        if (requireContext().hasPermission(Manifest.permission.ACCESS_FINE_LOCATION) &&
            requireContext().hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
        ) {
            viewModel.loadOriginByDeviceLocation()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        }

        binding.toolbar.title = getString(R.string.title_send_package)
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                android.R.id.home -> {
                    if (!findNavController().navigateUp()) {
                        requireActivity().finish()
                    }

                    true
                }

                else -> false
            }
        }

        lifecycleScope.launch {
            viewModel.loadOriginByDeviceLocation()
        }

        lifecycleScope.launch {
            viewModel.uiState
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .map { it.origin }
                .filterNotNull()
                .distinctUntilChanged()
                .collect {
                    fillOrigins(it)
                }
        }

        lifecycleScope.launch {
            viewModel.uiState
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .map { it.packageInfo }
                .filterNotNull()
                .distinctUntilChanged()
                .collect {
                    fillDetails(it)
                }
        }

        lifecycleScope.launch {
            fillDestinations(viewModel.uiState.value.destinations)
        }

        binding.addDestination.setOnClickListener {
            adapter.addEmptyItem()
        }

        binding.destinations.let {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(requireContext())
            it.addItemDecoration(MarginItemDecoration(24.dp))
        }

        binding.instantDelivery.setOnClickListener {
            processInstantDelivery()
        }

        return binding.root
    }

    private fun processInstantDelivery() {
        val origin = collectOrigin()
        val destinations = collectDestinations()
        val details = collectPackageDetails() ?: return

        viewModel.putOrderInfo(origin, destinations, details)

        navigateToConfirmation()
    }

    private fun collectOrigin(): OrderAddressDetails {
        val inputs = ItemAddressDetailsInputsBinding.bind(binding.originDetails.root)

        return OrderAddressDetails(
            address = inputs.address.text?.toString() ?: "",
            country = inputs.country.text?.toString() ?: "",
            phoneNumber = inputs.phone.text?.toString() ?: "",
            others = inputs.others.text?.toString() ?: "",
        )
    }

    private fun collectPackageDetails(): PackageDetails? {
        val itemsCountString = binding.packageItems.text?.toString()
        val itemWeightString = binding.itemsWeight.text?.toString()

        return when {
            itemsCountString.isNullOrBlank() || !itemsCountString.isDigitsOnly() -> {
                binding.packageItems.error = getString(R.string.error_invalid_number)
                null
            }
            itemWeightString.isNullOrBlank() || !itemWeightString.isDigitsOnly() -> {
                binding.itemsWeight.error = getString(R.string.error_invalid_number)
                null
            }
            else -> {
                PackageDetails(
                    itemsCount = itemsCountString.toInt(),
                    itemWeight = itemWeightString.toInt(),
                    worth = binding.packageItems.text?.toString() ?: "",
                )
            }
        }
    }

    private fun collectDestinations(): List<OrderAddressDetails> {
        return adapter.collectData()
            .filter { it.address.isNotBlank() || it.country.isNotBlank() || it.phoneNumber.isNotBlank() || it.others.isNotBlank() }
            .map {
                OrderAddressDetails(
                    it.address,
                    it.country,
                    it.phoneNumber,
                    it.others
                )
            }
    }

    private fun fillOrigins(origin: OrderAddressDetails) {
        val inputs = ItemAddressDetailsInputsBinding.bind(binding.originDetails.root)

        inputs.address.setText(origin.address)
        inputs.country.setText(origin.country)
        inputs.phone.setText(origin.phoneNumber)
        inputs.others.setText(origin.others)
    }

    private fun fillDestinations(destinations: List<OrderAddressDetails>) {
        adapter.setItems(
            destinations.map {
                DestinationContent(
                    address = it.address,
                    country = it.country,
                    phoneNumber = it.phoneNumber,
                    others = it.others ?: "",
                )
            }
        )
    }

    private fun fillDetails(details: PackageDetails) {
        binding.packageItems.setText(details.itemsCount.toString())
        binding.itemsWeight.setText(details.itemWeight.toString())
        binding.itemsWorth.setText(details.worth)
    }

    private fun navigateToConfirmation() {
        val navController = findNavController()

        val direction = EnterOrderDetailsFragmentDirections
            .actionEnterDetailsToConfirmDetails()

        navController.navigate(direction)
    }
}