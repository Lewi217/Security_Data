package com.example.key_generators.utils

object ValidationUtils {
    fun isValidEncryptionInput(text: String): Boolean {
        return text.isNotBlank() && text.length >= 3
    }

    fun isValidDecryptionInput(encryptedData: String, key: String): Boolean {
        return encryptedData.isNotBlank() && key.isNotBlank()
    }
}