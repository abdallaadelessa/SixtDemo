package com.abdullahessa.sixtdemo.ui.component

import androidx.navigation.NavHostController

/**
 * Models the navigation actions in the app.
 */
class AppRouter(
    val startRoute: AppRoute,
    val navController: NavHostController
) {
    fun navigateToHome() {
        navController.popBackStack()
        navController.navigate(AppRoute.HOME.route)
    }

    /**
     * Destinations used in the ([MainActivityContent]).
     */
    enum class AppRoute(val route: String) {
        SPLASH("/splash"),
        HOME("/home"),
    }
}
