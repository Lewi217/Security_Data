package com.example.key_generators.data.repository

import com.example.key_generators.data.model.DecryptionRequest
import com.example.key_generators.data.model.DecryptionResponse
import com.example.key_generators.data.model.EncryptionRequest
import com.example.key_generators.data.model.EncryptionResponse
import com.example.key_generators.data.remote.CryptoApiService
import com.example.key_generators.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CryptoRepository @Inject constructor(
    private val apiService: CryptoApiService
) {
    suspend fun encryptText(text: String): Flow<Resource<EncryptionResponse>> = flow {
        emit(Resource.Loading())
        try {
            val request = EncryptionRequest(text)
            val response = apiService.encrypt(request)

            when {
                response.isSuccessful && response.body()?.data != null -> {
                    emit(Resource.Success(response.body()!!.data!!))
                }
                else -> {
                    emit(Resource.Error(response.body()?.message ?: "Unknown error occurred"))
                }
            }
        } catch (e: HttpException) {
            emit(Resource.Error("Network error: ${e.message()}"))
        } catch (e: IOException) {
            emit(Resource.Error("Network connection error"))
        } catch (e: Exception) {
            emit(Resource.Error("An unexpected error occurred: ${e.localizedMessage}"))
        }
    }

    suspend fun decryptText(encryptedData: String, base64Key: String): Flow<Resource<DecryptionResponse>> = flow {
        emit(Resource.Loading())
        try {
            val request = DecryptionRequest(encryptedData, base64Key)
            val response = apiService.decrypt(request)

            when {
                response.isSuccessful && response.body()?.data != null -> {
                    emit(Resource.Success(response.body()!!.data!!))
                }
                else -> {
                    emit(Resource.Error(response.body()?.message ?: "Unknown error occurred"))
                }
            }
        } catch (e: HttpException) {
            emit(Resource.Error("Network error: ${e.message()}"))
        } catch (e: IOException) {
            emit(Resource.Error("Network connection error"))
        } catch (e: Exception) {
            emit(Resource.Error("An unexpected error occurred: ${e.localizedMessage}"))
        }
    }
}