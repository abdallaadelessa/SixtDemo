package com.abdullahessa.sixtdemo.ui.component


import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.abdullahessa.sixtdemo.R
import com.abdullahessa.sixtdemo.ui.component.AppRouter.AppRoute
import com.abdullahessa.sixtdemo.ui.screen.home.AppBottomNavigation
import com.abdullahessa.sixtdemo.ui.screen.home.ListScreen
import com.abdullahessa.sixtdemo.ui.screen.home.MapScreen
import com.abdullahessa.sixtdemo.ui.screen.home.model.HomeViewModel
import com.abdullahessa.sixtdemo.ui.screen.splash.SplashScreen
import com.google.accompanist.insets.systemBarsPadding

/**
 * Main app graph
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigationGraph(appRouter: AppRouter) {
    val homeViewModel = hiltViewModel<HomeViewModel>()

    val isFullScreen: MutableState<Boolean> = remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            if (isFullScreen.value) return@Scaffold
            HomeAppHeader(title = stringResource(id = R.string.app_name))
        },
        bottomBar = {
            if (isFullScreen.value) return@Scaffold
            AppBottomNavigation(appRouter)
        }
    ) { innerPadding ->
        NavHost(
            navController = appRouter.navController,
            startDestination = appRouter.startRoute.route,
            modifier = Modifier
                .systemBarsPadding()
                .padding(innerPadding)
        ) {
            composable(route = AppRoute.SPLASH.route) {
                isFullScreen.value = true
                CreateSplashScreen(appRouter::navigateToHome)
            }
            composable(route = AppRoute.LIST.route) {
                isFullScreen.value = false
                CreateListScreen(appRouter, homeViewModel)
            }
            composable(route = AppRoute.MAP.route) {
                isFullScreen.value = false
                CreateMapScreen(appRouter, homeViewModel)
            }
        }
    }
}

@Composable
private fun CreateSplashScreen(navigateToHome: () -> Unit) {
    SplashScreen(navigateToHome = navigateToHome)
}

@Composable
private fun CreateListScreen(appRouter: AppRouter, homeViewModel: HomeViewModel) {
    ListScreen(viewModel = homeViewModel)
}

@Composable
private fun CreateMapScreen(appRouter: AppRouter, homeViewModel: HomeViewModel) {
    MapScreen(viewModel = homeViewModel)
}
