package com.cruning.domain.repository

import androidx.paging.PagingData
import com.cruning.domain.model.ImageUrl
import kotlinx.coroutines.flow.Flow

interface DataRepository {
    fun getData(): Flow<PagingData<ImageUrl>>
}