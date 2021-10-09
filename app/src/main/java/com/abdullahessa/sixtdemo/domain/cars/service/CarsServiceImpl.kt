package com.abdullahessa.sixtdemo.domain.cars.service

import com.abdullahessa.sixtdemo.app.extensions.toEnumOrNull
import com.abdullahessa.sixtdemo.data.AppResult
import com.abdullahessa.sixtdemo.data.cars.service.CarsDataService
import com.abdullahessa.sixtdemo.domain.cars.model.CarModel
import javax.inject.Inject

/**
 * @author Created by Abdullah Essa on 08.10.21.
 */
class CarsServiceImpl @Inject constructor(
    private val dataService: CarsDataService
) : CarsService {
    override suspend fun getCarList(): AppResult<List<CarModel>> =
        dataService.getCarList()
            .mapSuccess { apiModels ->
                apiModels.map {
                    CarModel(
                        id = it.id,
                        driverName = it.name,
                        car = CarModel.CarInfo(
                            name = "${it.make} ${it.modelName}",
                            licensePlate = it.licensePlate,
                            color = getColor(it.color),
                            imageUrl = it.carImageUrl,
                            cleanliness = it.innerCleanliness?.toEnumOrNull(),
                        ),
                        location = CarModel.LocationInfo(
                            latitude = it.latitude,
                            longitude = it.longitude,
                        ),
                        fuel = CarModel.FuelInfo(
                            fuelLevel = it.fuelLevel?.let { it * 100 }?.toInt(),
                            fuelType = it.fuelType,
                        ),
                    )
                }
            }

    private fun getColor(color: String?) = color?.split('_')?.joinToString(separator = " ")
    { it.replaceFirstChar { char -> if (char.isLowerCase()) char.titlecase() else it } }
}
