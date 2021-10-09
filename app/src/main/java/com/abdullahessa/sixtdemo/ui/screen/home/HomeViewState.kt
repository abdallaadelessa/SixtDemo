package com.abdullahessa.sixtdemo.ui.screen.home

import android.os.Parcelable
import androidx.annotation.DrawableRes
import com.abdullahessa.sixtdemo.domain.cars.model.CarModel
import kotlinx.parcelize.Parcelize

/**
 * @author Created by Abdullah Essa on 08.10.21.
 */
sealed class HomeViewState(open val isRefreshing: Boolean) : Parcelable {
    @Parcelize
    data class Success(
        val cars: List<CarModel> = emptyList(),
        override val isRefreshing: Boolean = false
    ) : HomeViewState(isRefreshing)

    @Parcelize
    data class Error(
        @DrawableRes
        val drawableResId: Int,
        val message: String,
        override val isRefreshing: Boolean = false,
    ) : HomeViewState(isRefreshing)
}
