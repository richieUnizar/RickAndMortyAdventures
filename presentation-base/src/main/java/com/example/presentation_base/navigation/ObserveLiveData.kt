package com.example.presentation_base.navigation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

fun <T> observeLiveData2(navController: NavController, key: String): LiveData<T> {
    val liveData = navController.currentBackStackEntry?.savedStateHandle?.getLiveData<T>(key)
    if (liveData != null) {
        Log.d("observeLiveData", "LiveData encontrado para la clave: $key")
    } else {
        Log.w("observeLiveData", "No LiveData encontrado para la clave: $key")
    }
    return liveData ?: MutableLiveData<T>().also {
        Log.w("observeLiveData", "No LiveData encontrado para la clave: $key")
    }
}