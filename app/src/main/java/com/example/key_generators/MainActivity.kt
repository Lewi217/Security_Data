package com.example.key_generators

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.key_generators.ui.screens.CryptoScreen
import com.example.key_generators.ui.screens.SecurityWelcomeScreen
import com.example.key_generators.ui.theme.KeyGeneratorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KeyGeneratorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    var showWelcomeScreen by remember { mutableStateOf(true) }

                    NavHost(
                        navController = navController,
                        startDestination = if (showWelcomeScreen) "welcome" else "crypto"
                    ) {
                        composable("welcome") {
                            SecurityWelcomeScreen(
                                onAnimationComplete = {
                                    showWelcomeScreen = false
                                    navController.navigate("crypto")
                                }
                            )
                        }
                        composable("crypto") {
                            CryptoScreen()
                        }
                    }
                }
            }
        }
    }
}