package com.example.key_generators.utils

object Constants {
    // For physical device, use your computer's actual IP address
    // Example: "http://192.168.1.100:8081"
    // You can find your IP with 'ipconfig' (Windows) or 'ifconfig' (Mac/Linux)
    const val BASE_URL = "http://192.168.0.105:8081"
    const val ENCRYPTION_ENDPOINT = "/crypto/encrypt"
    const val DECRYPTION_ENDPOINT = "/crypto/decrypt"

    object PreferencesKeys {
        const val ENCRYPTED_DATA = "encrypted_data"
        const val ENCRYPTION_KEY = "encryption_key"
    }
}

//fun <T> Resource<T>.isSuccess() = this is Resource.Success
//fun <T> Resource<T>.isLoading() = this is Resource.Loading
//fun <T> Resource<T>.isError() = this is Resource.Error