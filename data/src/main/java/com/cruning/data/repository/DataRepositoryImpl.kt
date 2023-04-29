package com.cruning.data.repository

import com.cruning.data.rest.api.RestApi
import com.cruning.domain.repository.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class DataRepositoryImpl @Inject constructor(
    private val restApi: RestApi
) : DataRepository {

    override suspend fun getData(): List<String> = withContext(Dispatchers.IO) {
        emptyList()
    }
}

