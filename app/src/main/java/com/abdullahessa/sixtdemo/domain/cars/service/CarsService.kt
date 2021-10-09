package com.abdullahessa.sixtdemo.domain.cars.service

import com.abdullahessa.sixtdemo.data.AppResult
import com.abdullahessa.sixtdemo.data.cars.model.CarDataModel
import com.abdullahessa.sixtdemo.domain.cars.model.CarModel

/**
 * @author Created by Abdullah Essa on 08.10.21.
 */
interface CarsService {
    suspend fun getCarList(): AppResult<List<CarModel>>
}
