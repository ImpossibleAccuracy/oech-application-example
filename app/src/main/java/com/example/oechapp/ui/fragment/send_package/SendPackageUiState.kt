package com.example.oechapp.ui.fragment.send_package

import com.example.oechapp.data.model.OrderAddressDetails
import com.example.oechapp.data.model.PackageDetails

data class SendPackageUiState(
    val origin: OrderAddressDetails? = null,
    val destinations: List<OrderAddressDetails> = listOf(),
    val packageInfo: PackageDetails? = null
)
