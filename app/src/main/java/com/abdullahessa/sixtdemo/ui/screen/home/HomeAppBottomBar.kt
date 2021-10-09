package com.abdullahessa.sixtdemo.ui.screen.home

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Map
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.currentBackStackEntryAsState
import com.abdullahessa.sixtdemo.R
import com.abdullahessa.sixtdemo.ui.component.AppRouter

/**
 * @author Created by Abdullah Essa on 09.10.21.
 */
@Composable
fun AppBottomNavigation(router: AppRouter) {
    val navBackStackEntry: NavBackStackEntry? by router.navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    BottomNavigation {
        AppBottomNavigationItem(
            currentRoute = currentRoute,
            screenRoute = AppRouter.AppRoute.LIST.route,
            icon = Icons.Outlined.List,
            stringResId = R.string.tab_list
        ) {
            router.navigateToHomeList()
        }
        AppBottomNavigationItem(
            currentRoute = currentRoute,
            screenRoute = AppRouter.AppRoute.MAP.route,
            icon = Icons.Outlined.Map,
            stringResId = R.string.tab_map
        ) {
            router.navigateToHomeMap()
        }
    }
}

@Composable
private fun RowScope.AppBottomNavigationItem(
    currentRoute: String?,
    screenRoute: String,
    icon: ImageVector,
    @StringRes stringResId: Int,
    action: () -> Unit
) {
    BottomNavigationItem(
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = stringResource(id = stringResId)
            )
        },
        label = { Text(stringResource(id = stringResId)) },
        selected = screenRoute == currentRoute,
        onClick = { action() }
    )
}
