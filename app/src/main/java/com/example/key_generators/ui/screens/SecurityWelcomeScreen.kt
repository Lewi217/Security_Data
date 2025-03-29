package com.example.key_generators.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun SecurityWelcomeScreen(onAnimationComplete: () -> Unit) {
    val lockScale = remember { Animatable(1f) }
    val lockRotation = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        launch {
            lockScale.animateTo(
                targetValue = 1.2f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = 1000
                        1f at 500
                        1.2f at 1000
                    },
                    repeatMode = RepeatMode.Reverse
                )
            )
        }
        launch {
            lockRotation.animateTo(
                targetValue = 10f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = 500
                        0f at 250
                        10f at 500
                    },
                    repeatMode = RepeatMode.Reverse
                )
            )
        }
        onAnimationComplete()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Canvas(
                modifier = Modifier
                    .size(200.dp)
                    .scale(lockScale.value)
                    .rotate(lockRotation.value)
            ) {

                drawRect(
                    color = Color.Blue.copy(alpha = 0.7f),
                    style = Stroke(width = 10f),
                    topLeft = center.copy(x = center.x - 50f, y = center.y + 20f),
                    size = size.copy(width = 100f, height = 80f)
                )


                drawArc(
                    color = Color.Blue.copy(alpha = 0.7f),
                    startAngle = 0f,
                    sweepAngle = 180f,
                    useCenter = false,
                    style = Stroke(width = 10f),
                    topLeft = center.copy(x = center.x - 30f, y = center.y - 80f),
                    size = size.copy(width = 60f, height = 60f)
                )

                drawCircle(
                    color = Color.Blue.copy(alpha = 0.7f),
                    radius = 10f,
                    center = center.copy(y = center.y + 50f),
                    style = Fill
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Key Generators",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "Secure Your Data",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}