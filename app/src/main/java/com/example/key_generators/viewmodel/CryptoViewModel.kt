package com.example.key_generators.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.key_generators.data.model.DecryptionRequest
import com.example.key_generators.data.model.DecryptionResponse
import com.example.key_generators.data.model.EncryptionRequest
import com.example.key_generators.data.model.EncryptionResponse
import com.example.key_generators.data.remote.CryptoApiService
import com.example.key_generators.network.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class CryptoViewModel @Inject constructor(
    private val cryptoApiService: CryptoApiService
) : ViewModel() {

    private val _encryptionState = MutableStateFlow<UiState<EncryptionResponse>>(UiState.Initial)
    val encryptionState = _encryptionState.asStateFlow()

    private val _decryptionState = MutableStateFlow<UiState<DecryptionResponse>>(UiState.Initial)
    val decryptionState = _decryptionState.asStateFlow()

    fun encryptText(text: String) = viewModelScope.launch {
        _encryptionState.value = UiState.Loading
        try {
            val response = cryptoApiService.encrypt(EncryptionRequest(text))
            _encryptionState.value = handleResponse(response)
        } catch (e: Exception) {
            _encryptionState.value = UiState.Error(e.message ?: "Encryption failed")
        }
    }

    fun decryptText(encryptedData: String, base64Key: String) = viewModelScope.launch {
        _decryptionState.value = UiState.Loading
        try {
            val response = cryptoApiService.decrypt(DecryptionRequest(encryptedData, base64Key))
            _decryptionState.value = handleResponse(response)
        } catch (e: Exception) {
            _decryptionState.value = UiState.Error(e.message ?: "Decryption failed")
        }
    }

    private fun <T> handleResponse(response: Response<ApiResponse<T>>): UiState<T> {
        return if (response.isSuccessful) {
            response.body()?.data?.let {
                UiState.Success(it)
            } ?: UiState.Error("No data received")
        } else {
            UiState.Error(response.message())
        }
    }
}

sealed class UiState<out T> {
    object Initial : UiState<Nothing>()
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}