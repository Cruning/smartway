package com.cruning.presentation.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.cruning.domain.model.ImageUrl
import com.cruning.domain.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    dataRepository: DataRepository,
) : ViewModel() {

    private val _urls = MutableStateFlow<Flow<PagingData<ImageUrl>>>(flow { PagingData.empty<String>() })
    val urls = _urls.asStateFlow()

    init {
        _urls.value = dataRepository.getData().cachedIn(viewModelScope)
    }
}