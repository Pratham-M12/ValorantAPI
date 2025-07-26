package com.example.valorantapi.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import kotlinx.coroutines.delay

@Composable
fun LoginSuccessAnimation(onAnimationFinished: () -> Unit) {
    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("Success.json"))
    val animationState = animateLottieCompositionAsState(
        composition = composition,
        iterations = 1,
        speed = 1.5f
    )
    LaunchedEffect(animationState.isAtEnd && !animationState.isPlaying) {
        if (animationState.isAtEnd && !animationState.isPlaying) {
            delay(500) //Optional Delay for Polish
            onAnimationFinished()
        }
    }
    Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.8f)), contentAlignment = Alignment.Center)
    {
        LottieAnimation(composition = composition, progress = animationState.progress, modifier = Modifier.size(200.dp))
    }
}