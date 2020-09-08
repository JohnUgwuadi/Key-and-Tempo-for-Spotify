package com.johnugwuadi.keyandtempo.ui.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johnugwuadi.keyandtempo.data.remote.models.SearchResult
import com.johnugwuadi.keyandtempo.data.repositories.Result
import com.johnugwuadi.keyandtempo.data.repositories.search.SearchRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SearchViewModel @ViewModelInject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {
    private val _searchResultLiveData = MutableLiveData<SearchState>()
    val searchResultLiveData: LiveData<SearchState> = _searchResultLiveData

    var query: String? = null
        private set

    private val itemAmount = 3

    @ExperimentalCoroutinesApi
    @FlowPreview
    fun setSearchFlow(searchQuery: Flow<String>) {
        //Shows a loading state
        searchQuery.onEach { query ->
            if (this.query == query) {
                return@onEach
            }
            _searchResultLiveData.value = SearchState.Loading
        }.launchIn(viewModelScope)

        //Sets result
        searchQuery
            .debounce(200)
            .onEach { query ->
                if (this.query == query) {
                    return@onEach
                }

                this.query = query

                if (query.isNotBlank()) {
                    val result = searchRepository.search(query, itemAmount, 0)
                    when (result) {
                        is Result.Success -> {
                            _searchResultLiveData.value = if (result.data.isNotEmpty()) {
                                SearchState.Result(
                                    result.data
                                )
                            } else {
                                SearchState.NoResults(
                                    query
                                )
                            }
                        }
                        is Result.Error ->
                            _searchResultLiveData.value =
                                SearchState.Error(result.exception.message)
                    }
                } else {
                    _searchResultLiveData.value = SearchState.NoSearch
                }
            }
            .launchIn(viewModelScope)
    }

    sealed class SearchState {
        data class Result(val result: SearchResult) : SearchState()
        data class NoResults(val failedQuery: String) : SearchState()

        data class Error(val errorMessage: String?) : SearchState()

        object Loading : SearchState()
        object NoSearch : SearchState()
    }
}