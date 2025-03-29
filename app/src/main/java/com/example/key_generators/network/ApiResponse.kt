
package com.example.key_generators.network

data class ApiResponse<T>(
    val message: String = "Success",
    val data: T? = null
)