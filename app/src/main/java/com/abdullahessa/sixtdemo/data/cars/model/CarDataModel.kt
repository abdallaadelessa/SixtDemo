package com.abdullahessa.sixtdemo.data.cars.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CarDataModel(
    @SerialName("id")
    val id: String?,
    @SerialName("carImageUrl")
    val carImageUrl: String?,
    @SerialName("color")
    val color: String?,
    @SerialName("fuelLevel")
    val fuelLevel: Double?,
    @SerialName("fuelType")
    val fuelType: String?,
    @SerialName("group")
    val group: String?,
    @SerialName("innerCleanliness")
    val innerCleanliness: String?,
    @SerialName("latitude")
    val latitude: Double?,
    @SerialName("licensePlate")
    val licensePlate: String?,
    @SerialName("longitude")
    val longitude: Double?,
    @SerialName("make")
    val make: String?,
    @SerialName("modelIdentifier")
    val modelIdentifier: String?,
    @SerialName("modelName")
    val modelName: String?,
    @SerialName("name")
    val name: String?,
    @SerialName("series")
    val series: String?,
    @SerialName("transmission")
    val transmission: String?
)
