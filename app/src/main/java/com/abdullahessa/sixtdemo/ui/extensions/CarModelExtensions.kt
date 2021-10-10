package com.abdullahessa.sixtdemo.ui.extensions

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.abdullahessa.sixtdemo.R
import com.abdullahessa.sixtdemo.domain.cars.model.CarModel.CarInfo
import com.abdullahessa.sixtdemo.domain.cars.model.CarModel.FuelInfo

/**
 * @author Created by Abdullah Essa on 09.10.21.
 */
fun CarInfo.Cleanliness?.toColor(): Color =
    when (this) {
        CarInfo.Cleanliness.REGULAR -> Color(0xFF0277bd)
        CarInfo.Cleanliness.CLEAN -> Color(0xFF558b2f)
        CarInfo.Cleanliness.VERY_CLEAN -> Color(0xFF388e3c)
        null -> Color.Red
    }

@StringRes
fun CarInfo.Cleanliness?.toStringResId(): Int =
    when (this) {
        CarInfo.Cleanliness.CLEAN -> R.string.clean
        CarInfo.Cleanliness.VERY_CLEAN -> R.string.very_clean
        else -> R.string.regular
    }


fun FuelInfo?.levelColor(): Color {
    val level = this?.fuelLevel ?: return Color.Red
    return when {
        level <= 25 -> Color(0xFFb71c1c)
        level < 50 -> Color(0xFFef6c00)
        level < 75 -> Color(0xFFf9a825)
        else -> Color(0xFF388e3c)
    }
}
