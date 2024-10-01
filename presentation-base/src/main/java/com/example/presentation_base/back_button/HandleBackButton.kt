package com.example.presentation_base.back_button

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun HandleBackButton(navController: NavController) {
    BackHandler {
        navController.popBackStack()
    }
}
