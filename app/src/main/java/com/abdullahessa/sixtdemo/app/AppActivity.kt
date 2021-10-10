package com.abdullahessa.sixtdemo.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.abdullahessa.sixtdemo.ui.component.AppNavigationGraph
import com.abdullahessa.sixtdemo.ui.component.AppRouter
import com.alifwyaa.azanmunich.android.ui.theme.AppTheme
import com.google.accompanist.insets.ProvideWindowInsets
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController: NavHostController = rememberNavController()

            val appRouter: AppRouter =
                remember(navController) { AppRouter(navController = navController) }

            AppTheme(isDarkTheme = isSystemInDarkTheme()) {
                ProvideWindowInsets {
                    AppNavigationGraph(
                        appRouter = appRouter,
                    )
                }
            }
        }
    }
}
