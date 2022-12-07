package com.ludosupreme.exception

import com.ludosupreme.utils.LocationManager

class LocationException(
    val message: String? = "Error",
    val status: LocationManager.Status = LocationManager.Status.OTHER
) {
}