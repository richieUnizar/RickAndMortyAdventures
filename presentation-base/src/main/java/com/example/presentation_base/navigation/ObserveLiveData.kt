package com.example.presentation_base.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavController


@SuppressLint("UnrememberedMutableState")
@Composable
fun <T> observeLiveData(navController: NavController, key: String): State<T?> {
    return navController.currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<T>(key)
        ?.observeAsState()
        ?: mutableStateOf(null)
}