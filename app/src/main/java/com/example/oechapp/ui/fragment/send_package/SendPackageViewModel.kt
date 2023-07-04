package com.example.oechapp.ui.fragment.send_package

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oechapp.data.model.OrderAddressDetails
import com.example.oechapp.data.model.PackageDetails
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SendPackageViewModel @Inject constructor(
    @ApplicationContext private val context: Context
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

    fun loadOriginByDeviceLocation() {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val client = LocationServices.getFusedLocationProviderClient(context)

                val tokenSource = CancellationTokenSource()

                client.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, tokenSource.token)
                    .addOnSuccessListener {
                        onLocationLoaded(it)
                    }
            }
        }
    }

    private fun onLocationLoaded(location: Location?) {
        if (location == null) return

        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses =
            geocoder.getFromLocation(location.latitude, location.longitude, 1)

        addresses?.firstOrNull()?.let { address ->
            _uiState.update {
                val origin = OrderAddressDetails(
                    address = address.getAddressLine(0),
                    country = address.countryName,
                    phoneNumber = "",
                    others = ""
                )

                it.copy(origin = origin)
            }
        }
    }
}