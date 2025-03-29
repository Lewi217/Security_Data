package com.example.key_generators.ui.screens


import com.example.key_generators.viewmodel.CryptoViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.key_generators.viewmodel.UiState

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Encryption", style = MaterialTheme.typography.titleLarge)
        TextField(
            value = inputText,
            onValueChange = { inputText = it },
            label = { Text("Enter text to encrypt") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = { viewModel.encryptText(inputText) },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        ) {
            Text("Encrypt")
        }

        when (val state = encryptionState) {
            is UiState.Success -> {
                Text("Encrypted Data: ${state.data.encryptedData}")
                Text("Encryption Key: ${state.data.base64Key}")
                encryptedText = state.data.encryptedData
                decryptionKey = state.data.base64Key
            }
            is UiState.Loading -> CircularProgressIndicator()
            is UiState.Error -> Text("Error: ${state.message}", color = MaterialTheme.colorScheme.error)
            else -> {}
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Decryption", style = MaterialTheme.typography.titleLarge)
        TextField(
            value = encryptedText,
            onValueChange = { encryptedText = it },
            label = { Text("Encrypted Text") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = decryptionKey,
            onValueChange = { decryptionKey = it },
            label = { Text("Decryption Key") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = { viewModel.decryptText(encryptedText, decryptionKey) },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        ) {
            Text("Decrypt")
        }

        when (val state = decryptionState) {
            is UiState.Success -> {
                Text("Decrypted Text: ${state.data.decryptedText}")
                decryptedText = state.data.decryptedText
            }
            is UiState.Loading -> CircularProgressIndicator()
            is UiState.Error -> Text("Error: ${state.message}", color = MaterialTheme.colorScheme.error)
            else -> {}
        }
    }
}