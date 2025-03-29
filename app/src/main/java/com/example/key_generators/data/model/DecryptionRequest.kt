package com.example.key_generators.data.model

data class DecryptionRequest(
    val encryptedData: String,
    val base64Key: String
)