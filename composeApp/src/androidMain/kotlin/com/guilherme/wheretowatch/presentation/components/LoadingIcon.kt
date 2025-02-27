package com.guilherme.wheretowatch.presentation.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import wheretowatch.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.painterResource
import wheretowatch.composeapp.generated.resources.play_solid

@Composable
fun LoadingIcon(modifier: Modifier = Modifier) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black,
    ) {

        val alpha = remember { Animatable(1f) }

        LaunchedEffect(Unit) {
            launch {
                while (true) {
                    alpha.animateTo(
                        targetValue = 0.3f,
                        animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing)
                    )
                    alpha.animateTo(
                        targetValue = 1f,
                        animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing)
                    )
                }
            }
        }

        Box(
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.alpha(alpha.value).size(64.dp),
                painter = painterResource(Res.drawable.play_solid),
                contentDescription = "Loading Icon"
            )
        }
    }

}