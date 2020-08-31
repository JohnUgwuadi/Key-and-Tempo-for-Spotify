package com.johnugwuadi.keyandtempo.data.repositories.search

import com.johnugwuadi.keyandtempo.data.repositories.Result
import com.johnugwuadi.keyandtempo.data.remote.SpotifyService
import com.johnugwuadi.keyandtempo.data.remote.models.SearchResult
import com.johnugwuadi.keyandtempo.handleRetrofitErrors
import javax.inject.Inject

class DefaultSearchRepository @Inject constructor(
    private val spotifyService: SpotifyService
) : SearchRepository {
    override suspend fun search(query: String, limit: Int, offset: Int): Result<SearchResult> {
        return handleRetrofitErrors { spotifyService.search(query, limit, offset) }
    }
}