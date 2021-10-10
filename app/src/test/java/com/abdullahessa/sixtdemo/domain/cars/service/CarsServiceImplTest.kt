package com.abdullahessa.sixtdemo.domain.cars.service

import com.abdullahessa.sixtdemo.BaseTest
import com.abdullahessa.sixtdemo.data.AppHttpError
import com.abdullahessa.sixtdemo.data.AppResult
import com.abdullahessa.sixtdemo.data.cars.model.CarDataModel
import com.abdullahessa.sixtdemo.data.cars.service.CarsDataService
import com.abdullahessa.sixtdemo.domain.cars.model.CarModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @author Created by Abdullah Essa on 10.10.21.
 */
class CarsServiceImplTest : BaseTest() {
    @MockK
    private lateinit var dataService: CarsDataService

    @InjectMockKs
    private lateinit var objectUnderTest: CarsServiceImpl

    @Test
    fun `test domain layer mapping with Success`() = runBlockingTest {
        val dataModels = listOf(
            CarDataModel(
                id = "1",
                carImageUrl = "url1",
                color = "color_1",
                fuelLevel = 0.50,
                fuelType = "fuelType1",
                group = "group1",
                innerCleanliness = "clean",
                latitude = 1.0,
                longitude = 1.0,
                licensePlate = "licensePlate1",
                make = "make1",
                modelIdentifier = "modelIdentifier1",
                modelName = "modelName1",
                name = "name1",
                series = "series1",
                transmission = "transmission1",
            )
        )
        coEvery { dataService.getCarList() } returns AppResult.Success(dataModels)

        val expectedResult = AppResult.Success(
            listOf(
                CarModel(
                    id = "1",
                    driverName = "name1",
                    car = CarModel.CarInfo(
                        name = "make1 modelName1", // make + model
                        licensePlate = "licensePlate1",
                        color = "Color 1", // capitalize and remove underscores
                        imageUrl = "url1",
                        cleanliness = CarModel.CarInfo.Cleanliness.CLEAN,
                    ),
                    location = CarModel.LocationInfo(
                        latitude = 1.0,
                        longitude = 1.0
                    ),
                    fuel = CarModel.FuelInfo(
                        fuelLevel = 50, // x 100
                        fuelType = "fuelType1"
                    )
                )
            )
        )

        val actualResult: AppResult<List<CarModel>> = objectUnderTest.getCarList()

        coVerify(exactly = 1) { dataService.getCarList() }

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `test domain layer mapping with Error`() = runBlockingTest {
        val error = AppHttpError(code = 500, message = "error", NullPointerException("null"))

        coEvery { dataService.getCarList() } returns AppResult.Error(error)

        val expectedResult: AppResult.Error = AppResult.Error(error)

        val actualResult: AppResult<List<CarModel>> = objectUnderTest.getCarList()

        coVerify(exactly = 1) { dataService.getCarList() }

        assertEquals(expectedResult, actualResult)
    }
}
