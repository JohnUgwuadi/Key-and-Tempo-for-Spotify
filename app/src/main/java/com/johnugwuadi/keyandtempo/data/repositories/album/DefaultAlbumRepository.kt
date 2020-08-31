package com.johnugwuadi.keyandtempo.data.repositories.album

import com.johnugwuadi.keyandtempo.data.remote.models.TrackInAlbumResponse
import com.johnugwuadi.keyandtempo.data.remote.SpotifyService
import javax.inject.Inject
import com.johnugwuadi.keyandtempo.data.repositories.Result
import com.johnugwuadi.keyandtempo.handleRetrofitErrors

class DefaultAlbumRepository @Inject constructor(
    private val spotifyService: SpotifyService
) : AlbumRepository {
    override suspend fun getAlbumTracks(id: String): Result<List<TrackInAlbumResponse>> {
        return handleRetrofitErrors { spotifyService.getTracksInAlbum(id) }
    }
}