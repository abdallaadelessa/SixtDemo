package com.abdullahessa.sixtdemo.domain.cars.service

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
                        driverName = it.id,
                        car = CarModel.CarInfo(
                            group = it.group,
                            licensePlate = it.licensePlate,
                            colorInHex = it.color,
                            innerCleanliness = it.innerCleanliness,
                            imageUrl = it.carImageUrl,
                        ),
                        location = CarModel.LocationInfo(
                            latitude = it.latitude,
                            longitude = it.longitude,
                        ),
                        fuel = CarModel.FuelInfo(
                            fuelLevel = it.fuelLevel,
                            fuelType = it.fuelType,
                        ),
                    )
                }
            }
}
