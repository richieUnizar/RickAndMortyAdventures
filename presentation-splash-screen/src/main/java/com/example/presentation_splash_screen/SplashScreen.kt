package com.example.presentation_splash_screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.Navigation.findNavController
import com.example.domain.model.Characters
import com.example.domain.model.Info
import com.example.presentation_base.back_button.HandleBackButton
import com.example.presentation_base.navigation.NavigationItem
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLEncoder

@Composable
fun SplashScreen(navController: NavHostController) {

    val viewModel: SplashScreenViewModel = hiltViewModel()

    val characters by viewModel.characters.observeAsState()
    val loading by viewModel.loading.observeAsState()

    characters?.let { characterState ->
        if (loading == false && characterState != null) {
            LaunchedEffect(characterState) {
                navController.navigate("Home/$characterState") {
                    popUpTo(NavigationItem.Splash.route) { inclusive = true }
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.splash_image),
                contentDescription = "Splash Image",
                modifier = Modifier.size(300.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            CircularProgressIndicator(color = Color.Green)
        }
    }
}
