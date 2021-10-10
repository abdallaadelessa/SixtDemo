package com.abdullahessa.sixtdemo.ui.screen.home.model

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import com.abdullahessa.sixtdemo.BaseTest
import com.abdullahessa.sixtdemo.R
import com.abdullahessa.sixtdemo.data.AppConnectionError
import com.abdullahessa.sixtdemo.data.AppError
import com.abdullahessa.sixtdemo.data.AppHttpError
import com.abdullahessa.sixtdemo.data.AppResult
import com.abdullahessa.sixtdemo.domain.cars.model.CarModel
import com.abdullahessa.sixtdemo.domain.cars.service.CarsService
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verifyOrder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @author Created by Abdullah Essa on 10.10.21.
 */
class HomeViewModelTest : BaseTest() {
    private val savedStateHandle: SavedStateHandle = SavedStateHandle()

    @MockK
    private lateinit var carsService: CarsService

    @MockK
    private lateinit var appContext: Context

    private lateinit var objectUnderTest: HomeViewModel

    private val state: HomeViewState
        get() = (objectUnderTest.stateFlow as MutableStateFlow<HomeViewState>).value

    //region Tests

    @Test
    fun `test initial state`() = runBlockingTest {
        val carModels: List<CarModel> = listOf(
            mockk { every { id } returns "1" },
            mockk { every { id } returns "2" },
            mockk { every { id } returns "3" },
        )

        val noContentError = "no content error"

        every { appContext.getString(R.string.error_no_content) } returns noContentError

        coEvery { carsService.getCarList() } returns AppResult.Success(carModels)

        createViewModel()

        assertEquals(
            HomeViewState.Error(
                isRefreshing = false,
                message = noContentError,
                drawableResId = R.drawable.ic_no_data
            ),
            state
        )
    }

    @Test
    fun `test reload state with success and valid content`() = runBlockingTest {
        val carModels: List<CarModel> = listOf(
            mockk { every { id } returns "1" },
            mockk { every { id } returns "2" },
            mockk { every { id } returns "3" },
        )

        val errorMessage = "no content error"

        every { appContext.getString(R.string.error_no_content) } returns errorMessage

        coEvery { carsService.getCarList() } returns AppResult.Success(carModels)

        createViewModel()

        // Reload
        objectUnderTest.reload()

        verifyOrder {
            objectUnderTest.emitState(
                HomeViewState.Error(
                    isRefreshing = true,
                    message = errorMessage,
                    drawableResId = R.drawable.ic_no_data
                )
            )
            objectUnderTest.emitState(
                HomeViewState.Success(
                    isRefreshing = false,
                    cars = carModels
                )
            )
        }
    }

    @Test
    fun `test reload state with success but no content`() = runBlockingTest {
        val noContentError = "no content error"

        val carModels: List<CarModel> = listOf()

        val errorMessage = "no content error"

        every { appContext.getString(R.string.error_no_content) } returns errorMessage

        coEvery { carsService.getCarList() } returns AppResult.Success(carModels)

        createViewModel()

        // Reload
        objectUnderTest.reload()

        verifyOrder {
            objectUnderTest.emitState(
                HomeViewState.Error(
                    isRefreshing = true,
                    message = noContentError,
                    drawableResId = R.drawable.ic_no_data
                )
            )
            objectUnderTest.emitState(
                HomeViewState.Error(
                    isRefreshing = false,
                    message = noContentError,
                    drawableResId = R.drawable.ic_no_data
                )
            )
        }
    }

    @Test
    fun `test reload state with no connection error`() = runBlockingTest {
        val noContentError = "no content error"

        val noConnectionError = "no connection error"

        every { appContext.getString(R.string.error_no_content) } returns noContentError

        every { appContext.getString(R.string.error_no_connection) } returns noConnectionError

        coEvery { carsService.getCarList() } returns AppResult.Error(AppConnectionError(RuntimeException()))

        createViewModel()

        // Reload
        objectUnderTest.reload()

        verifyOrder {
            objectUnderTest.emitState(
                HomeViewState.Error(
                    isRefreshing = true,
                    message = noContentError,
                    drawableResId = R.drawable.ic_no_data
                )
            )
            objectUnderTest.emitState(
                HomeViewState.Error(
                    isRefreshing = false,
                    message = noConnectionError,
                    drawableResId = R.drawable.ic_no_connection
                )
            )
        }
    }

    @Test
    fun `test reload state with http error`() = runBlockingTest {
        val noContentError = "no content error"

        val httpError = "http error"

        every { appContext.getString(R.string.error_no_content) } returns noContentError

        every { appContext.getString(R.string.error_server_error) } returns httpError

        coEvery { carsService.getCarList() } returns AppResult.Error(AppHttpError(500, "", NullPointerException()))

        createViewModel()

        // Reload
        objectUnderTest.reload()

        verifyOrder {
            objectUnderTest.emitState(
                HomeViewState.Error(
                    isRefreshing = true,
                    message = noContentError,
                    drawableResId = R.drawable.ic_no_data
                )
            )
            objectUnderTest.emitState(
                HomeViewState.Error(
                    isRefreshing = false,
                    message = httpError,
                    drawableResId = R.drawable.ic_server_error
                )
            )
        }
    }

    @Test
    fun `test reload state with unknown error`() = runBlockingTest {
        val noContentError = "no content error"

        val unknownError = "unknown error"

        every { appContext.getString(R.string.error_no_content) } returns noContentError

        every { appContext.getString(R.string.error_unknown_error) } returns unknownError

        coEvery { carsService.getCarList() } returns AppResult.Error(AppError("error"))

        createViewModel()

        // Reload
        objectUnderTest.reload()

        verifyOrder {
            objectUnderTest.emitState(
                HomeViewState.Error(
                    isRefreshing = true,
                    message = noContentError,
                    drawableResId = R.drawable.ic_no_data
                )
            )
            objectUnderTest.emitState(
                HomeViewState.Error(
                    isRefreshing = false,
                    message = unknownError,
                    drawableResId = R.drawable.ic_unknown_error
                )
            )
        }
    }

    //endregion

    //region Helpers

    private fun createViewModel() {
        objectUnderTest = spyk(
            HomeViewModel(
                savedStateHandle = savedStateHandle,
                carsService = carsService,
                appContext = appContext,
                mainDispatcher = coroutinesTestRule.dispatcher
            )
        )
    }

    //endregion
}

