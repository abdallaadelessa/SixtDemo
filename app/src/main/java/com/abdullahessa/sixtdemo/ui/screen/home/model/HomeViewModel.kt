package com.abdullahessa.sixtdemo.ui.screen.home.model

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.abdullahessa.sixtdemo.R
import com.abdullahessa.sixtdemo.app.di.MainDispatcher
import com.abdullahessa.sixtdemo.data.AppConnectionError
import com.abdullahessa.sixtdemo.data.AppHttpError
import com.abdullahessa.sixtdemo.data.AppResult
import com.abdullahessa.sixtdemo.domain.cars.model.CarModel
import com.abdullahessa.sixtdemo.domain.cars.service.CarsService
import com.abdullahessa.sixtdemo.ui.screen.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Created by Abdullah Essa on 08.10.21.
 */
@HiltViewModel
@SuppressLint("StaticFieldLeak")
class HomeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val carsService: CarsService,
    @ApplicationContext
    private val appContext: Context,
    @MainDispatcher
    private val mainDispatcher: CoroutineDispatcher,
) : BaseViewModel(appContext) {

    //region Properties

    private val mutableStateFlow: MutableStateFlow<HomeViewState> = MutableStateFlow(
        HomeViewState.Success(isRefreshing = true)
    )

    private val state: HomeViewState get() = mutableStateFlow.value

    val stateFlow: Flow<HomeViewState> get() = mutableStateFlow

    //endregion

    init {
        reload()
    }

    //region Public Methods

    fun reload() {
        viewModelScope.launch(mainDispatcher) {
            // Update the old state
            computeAndUpdateState {
                when (it) {
                    is HomeViewState.Success -> it.copy(isRefreshing = true)
                    is HomeViewState.Error -> it.copy(isRefreshing = true)
                }
            }

            // Load Data and create the new state
            when (val result = carsService.getCarList()) {
                is AppResult.Success -> {
                    handleAppResultSuccess(result)
                }
                is AppResult.Error -> {
                    handleAppResultError(result)
                }
            }
        }
    }

    //endregion

    //region Helpers

    private fun handleAppResultSuccess(result: AppResult.Success<List<CarModel>>) {
        if (result.data.isEmpty()) {
            HomeViewState.Error(
                drawableResId = R.drawable.ic_splash_icon,
                message = R.string.error_no_content.get()
            )
        } else {
            HomeViewState.Success(cars = result.data)
        }
    }

    private fun handleAppResultError(result: AppResult.Error) {
        when (result.throwable) {
            is AppConnectionError -> {
                HomeViewState.Error(
                    drawableResId = R.drawable.ic_splash_icon,
                    message = R.string.error_no_connection.get()
                )
            }
            is AppHttpError -> {
                HomeViewState.Error(
                    drawableResId = R.drawable.ic_splash_icon,
                    message = R.string.error_server_error.get()
                )
            }
            else -> {
                HomeViewState.Error(
                    drawableResId = R.drawable.ic_splash_icon,
                    message = R.string.error_unknown_error.get()
                )
            }
        }
    }

    private fun computeAndUpdateState(updateStateBlock: (HomeViewState) -> HomeViewState = { it }) {
        val oldState = state
        val newState = updateStateBlock(oldState)
        // skip if the state is the same
        if (oldState == newState) return
        mutableStateFlow.tryEmit(newState)
    }

    //endregion
}
