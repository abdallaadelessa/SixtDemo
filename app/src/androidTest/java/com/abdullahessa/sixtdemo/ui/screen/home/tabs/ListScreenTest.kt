package com.abdullahessa.sixtdemo.ui.screen.home.tabs

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.abdullahessa.sixtdemo.R
import com.abdullahessa.sixtdemo.domain.cars.model.CarModel
import com.abdullahessa.sixtdemo.ui.screen.home.ListScreenContent
import com.abdullahessa.sixtdemo.ui.screen.home.model.HomeViewState
import com.alifwyaa.azanmunich.android.ui.theme.AppTheme
import org.junit.Rule
import org.junit.Test

/**
 * @author Created by Abdullah Essa on 10.10.21.
 */
class ListScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testSuccessState() {
        val carModel = CarModel(
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

        composeTestRule.setContent {
            AppTheme(isDarkTheme = false) {
                ListScreenContent(
                    state = HomeViewState.Success(cars = listOf(carModel)),
                    reload = {}
                )
            }
        }
        composeTestRule.onNodeWithText(carModel.driverName!!).assertIsDisplayed()
        composeTestRule.onNodeWithText(carModel.car!!.name!!).assertIsDisplayed()
        composeTestRule.onNodeWithText("(${carModel.car!!.licensePlate!!})").assertIsDisplayed()
        composeTestRule.onNodeWithText(carModel.car!!.color!!).assertIsDisplayed()
        composeTestRule.onNodeWithText("${carModel.fuel!!.fuelLevel!!}%").assertIsDisplayed()
    }

    @Test
    fun testErrorState() {
        val errorMessage = "Error Message"
        composeTestRule.setContent {
            AppTheme(isDarkTheme = false) {
                ListScreenContent(
                    state = HomeViewState.Error(R.drawable.ic_no_connection, errorMessage),
                    reload = {}
                )
            }
        }
        composeTestRule.onNodeWithContentDescription("Error Drawable").assertIsDisplayed()
        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
    }
}
