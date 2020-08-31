package com.johnugwuadi.keyandtempo.data.repositories.album

import com.johnugwuadi.keyandtempo.data.repositories.Result
import com.johnugwuadi.keyandtempo.data.remote.models.TrackInAlbumResponse

interface AlbumRepository {
    suspend fun getAlbumTracks(id: String): Result<List<TrackInAlbumResponse>>
}