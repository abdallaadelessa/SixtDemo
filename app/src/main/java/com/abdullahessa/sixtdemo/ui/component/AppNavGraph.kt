package com.abdullahessa.sixtdemo.ui.component


import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.abdullahessa.sixtdemo.R
import com.abdullahessa.sixtdemo.app.extensions.toEnumOrNull
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
    // Use the same instance for both tabs
    val homeViewModel = hiltViewModel<HomeViewModel>()

    Scaffold(
        topBar = {
            val navBackStackEntry: NavBackStackEntry? by appRouter.navController.currentBackStackEntryAsState()
            when (navBackStackEntry?.destination?.route?.toEnumOrNull<AppRoute>()) {
                AppRoute.MAP -> AppHeader(title = stringResource(id = R.string.home_Map))
                AppRoute.LIST -> AppHeader(title = stringResource(id = R.string.home_list))
                else -> return@Scaffold
            }
        },
        bottomBar = {
            val navBackStackEntry: NavBackStackEntry? by appRouter.navController.currentBackStackEntryAsState()
            when (navBackStackEntry?.destination?.route?.toEnumOrNull<AppRoute>()) {
                AppRoute.MAP, AppRoute.LIST -> AppBottomNavigation(appRouter)
                else -> return@Scaffold
            }
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
                CreateSplashScreen(appRouter::navigateToHome)
            }
            composable(route = AppRoute.LIST.route) {
                CreateListScreen(homeViewModel)
            }
            composable(route = AppRoute.MAP.route) {
                CreateMapScreen(homeViewModel)
            }
        }
    }
}

@Composable
private fun CreateSplashScreen(navigateToHome: () -> Unit) {
    SplashScreen(navigateToHome = navigateToHome)
}

@Composable
private fun CreateListScreen(homeViewModel: HomeViewModel) {
    ListScreen(viewModel = homeViewModel)
}

@Composable
private fun CreateMapScreen(homeViewModel: HomeViewModel) {
    MapScreen(viewModel = homeViewModel)
}
