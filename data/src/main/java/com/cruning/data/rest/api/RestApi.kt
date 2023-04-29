package com.cruning.data.rest.api

import com.cruning.data.rest.api.model.ImageDTO
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RestApi {
    @GET("photos")
    suspend fun getPhotos(
        @Header("Authorization") authHeader: String,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int,
    ): List<ImageDTO>
}