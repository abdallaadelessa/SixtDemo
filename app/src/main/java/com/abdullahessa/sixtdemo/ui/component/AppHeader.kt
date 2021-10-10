package com.abdullahessa.sixtdemo.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController

/**
 * @author Created by Abdullah Essa on 08.10.21.
 */
@Composable
fun AppHeader(
    modifier: Modifier = Modifier,
    title: String,
    statusBarColor: Color = MaterialTheme.colors.primary,
    backgroundColor: Color = MaterialTheme.colors.primary,
    contentColor: Color = MaterialTheme.colors.onPrimary,
) {

    val systemUiController = rememberSystemUiController()

    val useDarkIcons = MaterialTheme.colors.isLight

    SideEffect {
        systemUiController.setSystemBarsColor(color = statusBarColor, darkIcons = useDarkIcons)
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        TopAppBar(
            elevation = 2.dp,
            contentColor = contentColor,
            backgroundColor = backgroundColor,
            title = { TitleView(title) },
            modifier = modifier
        )
    }
}

@Composable
private fun TitleView(title: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = title,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}
