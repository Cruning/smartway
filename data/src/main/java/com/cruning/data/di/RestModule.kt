package com.cruning.data.di

import com.cruning.data.rest.api.RestApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(SingletonComponent::class)
class RestModule {
    @Provides
    fun provideRetrofit(): RestApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
            .create(RestApi::class.java)
    }

    companion object {
        private const val baseUrl = ""
    }
}