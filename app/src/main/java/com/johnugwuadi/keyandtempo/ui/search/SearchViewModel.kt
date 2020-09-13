package com.johnugwuadi.keyandtempo.ui.search

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.johnugwuadi.keyandtempo.data.remote.models.SearchResult
import com.johnugwuadi.keyandtempo.data.repositories.Result
import com.johnugwuadi.keyandtempo.data.repositories.search.SearchRepository

class SearchViewModel @ViewModelInject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {
    private val _searchResultLiveData = MutableLiveData<SearchState>()
    val searchResultLiveData: LiveData<SearchState> = _searchResultLiveData

    var query: String? = null
        private set

    private val itemAmount = 3

    suspend fun search(query: String) {
        Log.e("Search", query)
        if (this.query == query) {
            return
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

    sealed class SearchState {
        data class Result(val result: SearchResult) : SearchState()
        data class NoResults(val failedQuery: String) : SearchState()
        data class Error(val errorMessage: String?) : SearchState()
        object NoSearch : SearchState()
    }
}