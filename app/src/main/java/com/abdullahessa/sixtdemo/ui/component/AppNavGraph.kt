package com.abdullahessa.sixtdemo.ui.component


import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.abdullahessa.sixtdemo.R
import com.abdullahessa.sixtdemo.ui.component.AppRouter.AppRoute
import com.abdullahessa.sixtdemo.ui.screen.home.HomeScreen
import com.abdullahessa.sixtdemo.ui.screen.home.model.HomeViewModel
import com.abdullahessa.sixtdemo.ui.screen.splash.SplashScreen
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding

/**
 * Main app graph
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigationGraph(appRouter: AppRouter) {
    NavHost(
        navController = appRouter.navController,
        startDestination = appRouter.startRoute.route,
        modifier = Modifier
            .navigationBarsPadding()
            .statusBarsPadding()
    ) {
        composable(route = AppRoute.SPLASH.route) {
            CreateSplashScreen(appRouter::navigateToHome)
        }
        composable(route = AppRoute.HOME.route) {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            CreateHomeScreen(homeViewModel)
        }
    }
}

@Composable
private fun CreateSplashScreen(navigateToHome: () -> Unit) {
    Scaffold {
        SplashScreen(navigateToHome = navigateToHome)
    }
}

@Composable
private fun CreateHomeScreen(homeViewModel: HomeViewModel) {
    Scaffold(topBar = { HomeAppHeader(title = stringResource(id = R.string.app_name)) }) {
        HomeScreen(viewModel = homeViewModel)
    }
}
