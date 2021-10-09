package com.abdullahessa.sixtdemo.domain.cars.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @author Created by Abdullah Essa on 09.10.21.
 */
@Parcelize
data class CarModel(
    val id: String?,
    val driverName: String?,
    val car: CarInfo?,
    val location: LocationInfo?,
    val fuel: FuelInfo?,
) : Parcelable {
    @Parcelize
    data class CarInfo(
        val group: String?,
        val licensePlate: String?,
        val colorInHex: String?,
        val innerCleanliness: String?,
        val imageUrl: String?,
    ) : Parcelable

    @Parcelize
    data class LocationInfo(
        val latitude: Double?,
        val longitude: Double?,
    ) : Parcelable

    @Parcelize
    data class FuelInfo(
        val fuelLevel: Double?,
        val fuelType: String?,
    ) : Parcelable
}
