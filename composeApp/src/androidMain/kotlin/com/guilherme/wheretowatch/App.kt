package com.guilherme.wheretowatch

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.guilherme.wheretowatch.navigation.SetupNavGraph
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.theme.AppTheme
import ui.theme.displayFontFamily

@Composable
@Preview
fun App(navController: NavHostController) {
    AppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            SetupNavGraph(
                navController = navController
            )
        }
    }
}