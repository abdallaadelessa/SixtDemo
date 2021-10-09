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
        val name: String?,
        val licensePlate: String?,
        val color: String?,
        val imageUrl: String?,
        val cleanliness: Cleanliness?,
    ) : Parcelable {
        enum class Cleanliness {
            REGULAR,
            CLEAN,
            VERY_CLEAN,
        }
    }

    @Parcelize
    data class LocationInfo(
        val latitude: Double?,
        val longitude: Double?,
    ) : Parcelable

    @Parcelize
    data class FuelInfo(
        val fuelLevel: Int?,
        val fuelType: String?,
    ) : Parcelable
}
