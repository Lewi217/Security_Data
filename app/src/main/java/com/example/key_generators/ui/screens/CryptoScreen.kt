package com.example.key_generators.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.key_generators.viewmodel.CryptoViewModel
import com.example.key_generators.viewmodel.UiState
import kotlin.random.Random

data class Star(
    var x: Float,
    var y: Float,
    val size: Float,
    val alpha: Float,
    val pulseSpeed: Float
)

@Composable
fun CryptoScreen(
    viewModel: CryptoViewModel = hiltViewModel()
) {
    val encryptionState by viewModel.encryptionState.collectAsState()
    val decryptionState by viewModel.decryptionState.collectAsState()

    var inputText by remember { mutableStateOf("") }
    var encryptedText by remember { mutableStateOf("") }
    var decryptionKey by remember { mutableStateOf("") }
    var decryptedText by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    // Space theme colors
    val darkSpace = Color(0xFF050714)
    val deepBlue = Color(0xFF0A1128)
    val purpleNebula = Color(0xFF2E1065)
    val cosmicAccent = Color(0xFF8B5CF6)
    val starColor = Color(0xFFFFFFFF)
    val textFieldColor = Color(0xFF1A1C2A)

    // Generate random stars
    val stars = remember {
        List(200) {
            Star(
                x = Random.nextFloat() * 10000,
                y = Random.nextFloat() * 10000,
                size = Random.nextFloat() * 3f + 0.5f,
                alpha = Random.nextFloat() * 0.5f + 0.5f,
                pulseSpeed = Random.nextFloat() * 2f + 0.5f
            )
        }
    }

    // Star animation
    val pulseAnimation = rememberInfiniteTransition()
    val pulseFactor = pulseAnimation.animateFloat(
        initialValue = 0.7f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(darkSpace, deepBlue, purpleNebula),
                    startY = 0f,
                    endY = 3000f
                )
            )
    ) {
        // Star background
        Canvas(modifier = Modifier.fillMaxSize()) {
            stars.forEach { star ->
                val starAlpha = star.alpha * pulseFactor.value
                drawCircle(
                    color = starColor.copy(alpha = starAlpha),
                    radius = star.size,
                    center = Offset(star.x % size.width, star.y % size.height)
                )

                // Some stars with glow effect
                if (star.size > 2f) {
                    drawCircle(
                        color = starColor.copy(alpha = starAlpha * 0.3f),
                        radius = star.size * 3f,
                        center = Offset(star.x % size.width, star.y % size.height)
                    )
                }
            }

            // Draw a few nebula-like fuzzy areas
            for (i in 0 until 5) {
                val x = (Random.nextFloat() * size.width)
                val y = (Random.nextFloat() * size.height)
                drawCircle(
                    color = cosmicAccent.copy(alpha = 0.05f),
                    radius = Random.nextFloat() * 300f + 100f,
                    center = Offset(x, y)
                )
            }
        }

        // Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            Text(
                text = "CRYPTO ENCRYPTION",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Encryption Section
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF111328).copy(alpha = 0.8f)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        "Encryption",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = inputText,
                        onValueChange = { inputText = it },
                        label = { Text("Enter text to encrypt", color = Color.White.copy(alpha = 0.7f)) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = textFieldColor,
                            unfocusedContainerColor = textFieldColor,
                            focusedBorderColor = cosmicAccent,
                            unfocusedBorderColor = Color.Gray,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { viewModel.encryptText(inputText) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = cosmicAccent
                        )
                    ) {
                        Text("Encrypt", color = Color.White)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    when (val state = encryptionState) {
                        is UiState.Success -> {
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                color = Color(0xFF1A1C2A).copy(alpha = 0.9f),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    Text(
                                        "Encrypted Data:",
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        state.data.encryptedData,
                                        color = Color.White.copy(alpha = 0.8f)
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Text(
                                        "Encryption Key:",
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        state.data.base64Key,
                                        color = cosmicAccent.copy(alpha = 0.9f)
                                    )

                                    LaunchedEffect(state) {
                                        encryptedText = state.data.encryptedData
                                        decryptionKey = state.data.base64Key
                                    }
                                }
                            }
                        }
                        is UiState.Loading -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(color = cosmicAccent)
                            }
                        }
                        is UiState.Error -> {
                            Text(
                                "Error: ${state.message}",
                                color = Color.Red,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }
                        else -> {}
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Decryption Section
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF111328).copy(alpha = 0.8f)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        "Decryption",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = encryptedText,
                        onValueChange = { encryptedText = it },
                        label = { Text("Encrypted Text", color = Color.White.copy(alpha = 0.7f)) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = textFieldColor,
                            unfocusedContainerColor = textFieldColor,
                            focusedBorderColor = cosmicAccent,
                            unfocusedBorderColor = Color.Gray,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = decryptionKey,
                        onValueChange = { decryptionKey = it },
                        label = { Text("Decryption Key", color = Color.White.copy(alpha = 0.7f)) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = textFieldColor,
                            unfocusedContainerColor = textFieldColor,
                            focusedBorderColor = cosmicAccent,
                            unfocusedBorderColor = Color.Gray,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { viewModel.decryptText(encryptedText, decryptionKey) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = cosmicAccent
                        )
                    ) {
                        Text("Decrypt", color = Color.White)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    when (val state = decryptionState) {
                        is UiState.Success -> {
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                color = Color(0xFF1A1C2A).copy(alpha = 0.9f),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    Text(
                                        "Decrypted Text:",
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        state.data.decryptedText,
                                        color = Color.White.copy(alpha = 0.8f)
                                    )

                                    LaunchedEffect(state) {
                                        decryptedText = state.data.decryptedText
                                    }
                                }
                            }
                        }
                        is UiState.Loading -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(color = cosmicAccent)
                            }
                        }
                        is UiState.Error -> {
                            Text(
                                "Error: ${state.message}",
                                color = Color.Red,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }
                        else -> {}
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}