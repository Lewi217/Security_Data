package com.example.key_generators.di

import com.example.key_generators.data.remote.CryptoApiService
import com.example.key_generators.data.repository.CryptoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideCryptoRepository(
        apiService: CryptoApiService
    ): CryptoRepository {
        return CryptoRepository(apiService)
    }
}