package com.example.arogyanidhi.di

import com.example.arogyanidhi.data.repository.AuthRepositoryImpl
import com.example.arogyanidhi.data.repository.UserRepositoryImpl
import com.example.arogyanidhi.domain.repository.AuthRepository
import com.example.arogyanidhi.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository
}
