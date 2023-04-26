package com.cruning.domain.repository


interface DataRepository {
    suspend fun getData(): List<String>
}