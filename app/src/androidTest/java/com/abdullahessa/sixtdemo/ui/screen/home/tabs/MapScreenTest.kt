package com.abdullahessa.sixtdemo.ui.screen.home.tabs

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.abdullahessa.sixtdemo.R
import com.abdullahessa.sixtdemo.ui.screen.home.MapScreenContent
import com.abdullahessa.sixtdemo.ui.screen.home.model.HomeViewState
import com.alifwyaa.azanmunich.android.ui.theme.AppTheme
import org.junit.Rule
import org.junit.Test

/**
 * @author Created by Abdullah Essa on 10.10.21.
 */
class MapScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testErrorState() {
        val errorMessage = "Error Message"
        composeTestRule.setContent {
            AppTheme(isDarkTheme = false) {
                MapScreenContent(
                    state = HomeViewState.Error(R.drawable.ic_no_connection, errorMessage),
                    reload = {}
                )
            }
        }
        composeTestRule.onNodeWithContentDescription("Error Drawable").assertIsDisplayed()
        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
    }
}
