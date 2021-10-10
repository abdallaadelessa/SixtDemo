package com.abdullahessa.sixtdemo.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.abdullahessa.sixtdemo.domain.cars.model.CarModel
import com.abdullahessa.sixtdemo.ui.component.rememberMapViewWithLifecycle
import com.abdullahessa.sixtdemo.ui.extensions.moveCameraToCenter
import com.abdullahessa.sixtdemo.ui.screen.home.model.HomeViewModel
import com.abdullahessa.sixtdemo.ui.screen.home.model.HomeViewState
import com.google.android.libraries.maps.model.LatLng
import com.google.android.libraries.maps.model.Marker
import com.google.android.libraries.maps.model.MarkerOptions
import com.google.maps.android.ktx.awaitMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**
 * @author Created by Abdullah Essa on 08.10.21.
 */

//region Screen

@Composable
fun MapScreen(viewModel: HomeViewModel) {
    val state: HomeViewState by viewModel.stateFlow.collectAsState(viewModel.initialState)
    MapScreenContent(
        state = state,
        reload = viewModel::reload,
    )
}

@Composable
fun MapScreenContent(
    state: HomeViewState,
    reload: () -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.align(Alignment.Center)
            ) {
                when (state) {
                    is HomeViewState.Success -> MapItem(carModels = state.cars)
                    is HomeViewState.Error -> ErrorItem(state = state)
                }
            }
            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp),
                onClick = reload
            ) {
                Icon(imageVector = Icons.Outlined.Refresh, contentDescription = null)
            }
        }
    }
}

//endregion

//region Items

@Composable
fun MapItem(carModels: List<CarModel>) {
    val mapViewWithLifecycle = rememberMapViewWithLifecycle()
    AndroidView({ mapViewWithLifecycle }) { mapView ->
        CoroutineScope(Dispatchers.Main).launch {
            mapView.awaitMap().apply {
                uiSettings.isZoomControlsEnabled = true
                // Create marker options
                val options: List<MarkerOptions> = carModels.mapNotNull { carModel ->
                    val carInfo: CarModel.CarInfo = carModel.car ?: return@mapNotNull null
                    val locInfo: CarModel.LocationInfo = carModel.location ?: return@mapNotNull null
                    val latitude = locInfo.latitude ?: return@mapNotNull null
                    val longitude = locInfo.longitude ?: return@mapNotNull null
                    MarkerOptions()
                        .title(carInfo.name)
                        .snippet(carInfo.licensePlate)
                        .position(LatLng(latitude, longitude))
                }
                // Add markers
                val markers: List<Marker> = options.map { addMarker(it) }
                // Center camera
                moveCameraToCenter(markers)
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
            contentDescription = "Error Drawable",
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
