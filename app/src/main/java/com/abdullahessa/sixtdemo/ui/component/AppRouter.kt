package com.abdullahessa.sixtdemo.ui.component

import androidx.navigation.NavGraph.Companion.findStartDestination
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
        navigateToTab(AppRoute.LIST.route)
    }

    fun navigateToHomeList() {
        navigateToTab(AppRoute.LIST.route)
    }

    fun navigateToHomeMap() {
        navigateToTab(AppRoute.MAP.route)
    }

    private fun navigateToTab(route: String) {
        navController.navigate(route) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            navController.graph.startDestinationRoute?.let { route ->
                popUpTo(route) {
                    saveState = true
                }
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
    }

    /**
     * Destinations used in the ([MainActivityContent]).
     */
    enum class AppRoute(val route: String) {
        SPLASH("/splash"),
        MAP("/map"),
        LIST("/list"),
    }
}
