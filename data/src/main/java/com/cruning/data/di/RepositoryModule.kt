package com.cruning.data.di

import com.cruning.data.repository.DataRepositoryImpl
import com.cruning.domain.repository.DataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindsDataRepository(dataRepositoryImpl: DataRepositoryImpl): DataRepository
}