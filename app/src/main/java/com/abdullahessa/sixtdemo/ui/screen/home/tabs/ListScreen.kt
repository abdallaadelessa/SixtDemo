package com.abdullahessa.sixtdemo.ui.screen.home

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.alpha
import androidx.core.graphics.blue
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.graphics.green
import androidx.core.graphics.red
import coil.compose.rememberImagePainter
import com.abdullahessa.sixtdemo.R
import com.abdullahessa.sixtdemo.domain.cars.model.CarModel
import com.abdullahessa.sixtdemo.ui.extensions.levelColor
import com.abdullahessa.sixtdemo.ui.extensions.toColor
import com.abdullahessa.sixtdemo.ui.screen.home.model.HomeViewModel
import com.abdullahessa.sixtdemo.ui.screen.home.model.HomeViewState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

/**
 * @author Created by Abdullah Essa on 08.10.21.
 */

//region Screen

@Composable
fun ListScreen(viewModel: HomeViewModel) {
    val state: HomeViewState by viewModel.stateFlow.collectAsState(viewModel.initialState)
    ListContent(
        state = state,
        reload = viewModel::reload
    )
}

@Composable
fun ListContent(
    state: HomeViewState,
    reload: () -> Unit = {}
) {
    SwipeRefresh(
        state = rememberSwipeRefreshState(state.isRefreshing),
        onRefresh = { reload() },
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(
                horizontal = 16.dp,
                vertical = 16.dp
            ),
            modifier = Modifier.fillMaxSize(),
        ) {
            when (state) {
                is HomeViewState.Success -> {
                    state.cars.forEach {
                        item {
                            CarItem(it)
                        }
                    }
                }
                is HomeViewState.Error -> {
                    item { ErrorItem(state) }
                }
            }
        }
    }
}

//endregion

//region Items

@Composable
private fun CarItem(carModel: CarModel) {
    Card(
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, MaterialTheme.colors.onPrimary),
        elevation = 4.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Image(
                painter = rememberImagePainter(
                    data = carModel.car?.imageUrl,
                    builder = {
                        crossfade(true)
                        val drawable: Drawable? = getFallbackDrawable()
                        placeholder(drawable)
                        error(drawable)
                        fallback(drawable)


                    }
                ),
                contentScale = ContentScale.Fit,
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 16.dp)
            )
            Column {
                DriverItem(carModel)
                CarDetailsItem(carModel)
                CarStatusItem(carModel)
                CarLicenseItem(carModel)
            }

        }
    }
}

@Composable
private fun DriverItem(carModel: CarModel) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Filled.AccountCircle,
            contentDescription = null
        )
        Text(
            modifier = Modifier.padding(start = 4.dp),
            text = carModel.driverName.orEmpty(),
            style = MaterialTheme.typography.h6,
        )
    }
}

@Composable
private fun CarDetailsItem(carModel: CarModel) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1.0f, true),
        ) {
            Text(
                text = carModel.car?.name.orEmpty(),
                style = MaterialTheme.typography.subtitle1,
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "${carModel.fuel?.fuelLevel}%",
                style = MaterialTheme.typography.subtitle1,
                color = carModel.fuel.levelColor()
            )
            Image(
                painter = painterResource(R.drawable.ic_fuel),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.onPrimary),
            )
        }
    }
}

@Composable
private fun CarStatusItem(carModel: CarModel) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1.0f, true),
        ) {
            Text(
                text = carModel.car?.color.orEmpty(),
                style = MaterialTheme.typography.subtitle2,
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(R.drawable.ic_cleanliness),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                colorFilter = ColorFilter.tint(carModel.car?.cleanliness.toColor()),
            )

        }
    }
}

@Composable
private fun CarLicenseItem(carModel: CarModel) {
    Text(
        text = "(${carModel.car?.licensePlate})",
        style = MaterialTheme.typography.subtitle2,
    )
}

@Composable
private fun ErrorItem(state: HomeViewState.Error) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(state.drawableResId),
            contentDescription = null,
            modifier = Modifier
                .size(120.dp)
                .padding(end = 16.dp)
        )
        Text(
            text = state.message,
            style = MaterialTheme.typography.h6,
        )
    }
}

//endregion

//region Helpers

@Composable
private fun getFallbackDrawable(): Drawable? {
    val current: Context = LocalContext.current
    val drawable: Drawable? = AppCompatResources.getDrawable(current, R.drawable.ic_car_fallback)
    if (drawable != null) {
        val colorArgb = MaterialTheme.colors.onPrimary.toArgb()
        DrawableCompat.setTint(
            drawable,
            Color.argb(
                colorArgb.alpha,
                colorArgb.red,
                colorArgb.green,
                colorArgb.blue
            )
        )
    }
    return drawable
}

//endregion
