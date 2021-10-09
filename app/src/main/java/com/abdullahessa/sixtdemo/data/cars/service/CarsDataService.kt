package com.abdullahessa.sixtdemo.data.cars.service

import com.abdullahessa.sixtdemo.data.AppResult
import com.abdullahessa.sixtdemo.data.cars.model.CarDataModel

/**
 * @author Created by Abdullah Essa on 08.10.21.
 */
interface CarsDataService {
    suspend fun getCarList(): AppResult<List<CarDataModel>>
}
