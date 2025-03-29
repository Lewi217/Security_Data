package com.example.key_generators.data.model

data class EncryptionResponse(
    val encryptedData: String,
    val base64Key: String
)