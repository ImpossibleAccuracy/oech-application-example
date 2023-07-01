package com.example.oechapp.ui.fragment.send_package

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oechapp.data.model.OrderAddressDetails
import com.example.oechapp.data.model.PackageDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SendPackageViewModel @Inject constructor(

) : ViewModel() {
    private val _uiState = MutableStateFlow(SendPackageUiState())
    val uiState = _uiState.asStateFlow()

    fun putOrderInfo(
        origin: OrderAddressDetails,
        destinations: List<OrderAddressDetails>,
        details: PackageDetails
    ) {
        viewModelScope.launch {
            setOriginDetails(origin)
            setDestinationDetails(destinations)
            setPackageDetails(details)
        }
    }

    fun setOriginDetails(origin: OrderAddressDetails) {
        _uiState.update {
            it.copy(origin = origin)
        }
    }

    fun setDestinationDetails(destinations: List<OrderAddressDetails>) {
        _uiState.update {
            it.copy(destinations = destinations)
        }
    }

    fun setPackageDetails(details: PackageDetails) {
        _uiState.update {
            it.copy(packageInfo = details)
        }
    }
}