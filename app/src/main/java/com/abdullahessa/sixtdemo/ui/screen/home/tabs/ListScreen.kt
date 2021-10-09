package com.abdullahessa.sixtdemo.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
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
                horizontal = 8.dp,
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

@Composable
private fun CarItem(carModel: CarModel) {
    Card(
        shape = RoundedCornerShape(10.dp),
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
                        placeholder(R.drawable.ic_splash_icon)
                        error(R.drawable.ic_splash_icon)
                        fallback(R.drawable.ic_splash_icon)
                    }
                ),
                contentScale = ContentScale.Fit,
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 16.dp)
            )

            Column {
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
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1.0f, true),
                    ) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(carModel.car?.cleanliness.toColor())
                        )
                        Text(
                            modifier = Modifier.padding(horizontal = 8.dp),
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
                            modifier = Modifier
                                .size(20.dp)
                        )
                    }
                }
                Text(
                    text = carModel.car?.color.orEmpty(),
                    style = MaterialTheme.typography.subtitle2,
                )
                Text(
                    text = "(${carModel.car?.licensePlate})",
                    style = MaterialTheme.typography.subtitle2,
                )

            }

        }
    }
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
