package com.example.key_generators.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun SecurityWelcomeScreen(onAnimationComplete: () -> Unit) {

    val darkBackground = Color(0xFF121212)
    val accentGreen = Color(0xFF00FF41)
    val accentBlue = Color(0xFF0077FF)
    val darkBlue = Color(0xFF002244)

    val lockScale = remember { Animatable(1f) }
    val lockRotation = remember { Animatable(0f) }
    val textAlpha = remember { Animatable(0f) }
    val sliderPosition = remember { Animatable(0f) }
    val matrixRain = remember { mutableStateListOf<MatrixRainDrop>() }
    val scope = rememberCoroutineScope()


    val binaryText = remember { mutableStateOf("") }
    val binaryChars = listOf("0", "1")

    LaunchedEffect(Unit) {
        repeat(50) {
            val x = Random.nextFloat() * 1080
            val y = Random.nextFloat() * 500 - 500
            val speed = Random.nextFloat() * 5 + 2
            val alpha = Random.nextFloat() * 0.6f + 0.2f
            matrixRain.add(MatrixRainDrop(x, y, speed, alpha))
        }

        while (true) {
            matrixRain.forEachIndexed { index, drop ->
                matrixRain[index] = drop.copy(
                    y = if (drop.y > 2000) Random.nextFloat() * 500 - 500 else drop.y + drop.speed,
                    x = if (drop.y > 2000) Random.nextFloat() * 1080 else drop.x
                )
            }
            delay(16)
        }
    }

    LaunchedEffect(Unit) {
        while (true) {
            val text = StringBuilder()
            repeat(20) {
                text.append(binaryChars.random())
            }
            binaryText.value = text.toString()
            delay(150)
        }
    }

    LaunchedEffect(Unit) {
        launch {
            lockScale.animateTo(
                targetValue = 1.2f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = 1500
                        1f at 0
                        1.1f at 750
                        1f at 1500
                    },
                    repeatMode = RepeatMode.Restart
                )
            )
        }

        launch {
            lockRotation.animateTo(
                targetValue = 5f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = 1000
                        0f at 0
                        5f at 500
                        0f at 1000
                    },
                    repeatMode = RepeatMode.Restart
                )
            )
        }

        launch {
            delay(500)
            textAlpha.animateTo(1f, animationSpec = tween(1000))

            delay(500)
            sliderPosition.animateTo(1f, animationSpec = tween(1500))

            delay(1000)
            onAnimationComplete()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(darkBackground, Color(0xFF0A0A14)),
                    startY = 0f,
                    endY = Float.POSITIVE_INFINITY
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        // Matrix rain effect
        Canvas(modifier = Modifier.fillMaxSize()) {
            matrixRain.forEach { drop ->
                drawCircle(
                    color = accentGreen.copy(alpha = drop.alpha),
                    radius = 2f,
                    center = Offset(drop.x, drop.y)
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.1f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = binaryText.value,
                style = MaterialTheme.typography.bodySmall,
                color = accentGreen.copy(alpha = 0.5f),
                modifier = Modifier.graphicsLayer {
                    scaleX = 10f
                    scaleY = 10f
                }
            )
        }


        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {

            Canvas(
                modifier = Modifier
                    .size(150.dp)
                    .scale(lockScale.value)
                    .rotate(lockRotation.value)
            ) {

                drawRect(
                    color = accentBlue.copy(alpha = 0.8f),
                    style = Stroke(width = 8f),
                    topLeft = center.copy(x = center.x - 50f, y = center.y + 20f),
                    size = size.copy(width = 100f, height = 80f)
                )


                drawArc(
                    color = accentBlue.copy(alpha = 0.8f),
                    startAngle = 0f,
                    sweepAngle = 180f,
                    useCenter = false,
                    style = Stroke(width = 8f),
                    topLeft = center.copy(x = center.x - 30f, y = center.y - 50f),
                    size = size.copy(width = 60f, height = 60f)
                )

                drawCircle(
                    color = accentBlue.copy(alpha = 0.8f),
                    radius = 10f,
                    center = center.copy(y = center.y + 60f),
                    style = Fill
                )


                for (i in 0 until 8) {
                    val startX = center.x - 120f + (i * 30)
                    drawLine(
                        color = accentGreen.copy(alpha = 0.6f),
                        start = Offset(startX, center.y + 120f),
                        end = Offset(startX + 15f, center.y + 140f),
                        strokeWidth = 2f
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "KEY GENERATORS",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.alpha(textAlpha.value)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "SECURE YOUR DIGITAL FORTRESS",
                style = MaterialTheme.typography.bodyLarge,
                color = accentGreen,
                modifier = Modifier.alpha(textAlpha.value)
            )

            Spacer(modifier = Modifier.height(48.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(60.dp)
                    .background(
                        color = darkBlue,
                        shape = MaterialTheme.shapes.medium
                    )
                    .padding(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(sliderPosition.value)
                        .fillMaxHeight()
                        .background(
                            Brush.horizontalGradient(
                                listOf(accentGreen.copy(alpha = 0.3f), accentGreen)
                            ),
                            shape = MaterialTheme.shapes.medium
                        )
                )

                Text(
                    text = "Key Generators Loading...",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

data class MatrixRainDrop(val x: Float, val y: Float, val speed: Float, val alpha: Float)