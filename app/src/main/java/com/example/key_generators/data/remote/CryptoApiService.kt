package com.example.key_generators.data.remote

import com.example.key_generators.data.model.DecryptionRequest
import com.example.key_generators.data.model.DecryptionResponse
import com.example.key_generators.data.model.EncryptionRequest
import com.example.key_generators.data.model.EncryptionResponse
import com.example.key_generators.network.ApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface CryptoApiService {
    @POST("/crypto/encrypt")
    suspend fun encrypt(@Body request: EncryptionRequest): Response<ApiResponse<EncryptionResponse>>

    @POST("/crypto/decrypt")
    suspend fun decrypt(@Body request: DecryptionRequest): Response<ApiResponse<DecryptionResponse>>
}