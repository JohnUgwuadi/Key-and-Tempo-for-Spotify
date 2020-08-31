package com.johnugwuadi.keyandtempo.data.repositories.search

import com.johnugwuadi.keyandtempo.data.repositories.Result
import com.johnugwuadi.keyandtempo.data.remote.models.SearchResult

interface SearchRepository {
    suspend fun search(query: String, limit: Int, offset: Int): Result<SearchResult>
}