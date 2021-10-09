package com.abdullahessa.sixtdemo.ui.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.abdullahessa.sixtdemo.ui.screen.home.model.HomeViewModel
import com.abdullahessa.sixtdemo.ui.screen.home.model.HomeViewState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

/**
 * @author Created by Abdullah Essa on 08.10.21.
 */
@Composable
fun MapScreen(viewModel: HomeViewModel) {
    val state: HomeViewState by viewModel.stateFlow.collectAsState(viewModel.initialState)
    MapContent(
        state = state,
        reload = viewModel::reload
    )
}

@Composable
fun MapContent(
    state: HomeViewState,
    reload: () -> Unit = {}
) {
    SwipeRefresh(
        state = rememberSwipeRefreshState(state.isRefreshing),
        onRefresh = { reload() },
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(
                horizontal = 24.dp,
                vertical = 16.dp
            ),
            modifier = Modifier.fillMaxWidth(),
        ) {

        }
    }
}
