package com.johnugwuadi.keyandtempo.data.repositories.artists

import com.johnugwuadi.keyandtempo.data.repositories.Result
import com.johnugwuadi.keyandtempo.data.remote.models.ArtistResponse
import com.johnugwuadi.keyandtempo.data.remote.SpotifyService
import com.johnugwuadi.keyandtempo.handleRetrofitErrors
import javax.inject.Inject

class DefaultArtistRepository @Inject constructor(
    private val spotifyService: SpotifyService
) : ArtistRepository {
    override suspend fun getArtist(id: String): Result<ArtistResponse> {
        return handleRetrofitErrors { spotifyService.artist(id) }
    }
}