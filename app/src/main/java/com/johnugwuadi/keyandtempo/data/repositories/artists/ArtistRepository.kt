package com.johnugwuadi.keyandtempo.data.repositories.artists

import com.johnugwuadi.keyandtempo.data.repositories.Result
import com.johnugwuadi.keyandtempo.data.remote.models.ArtistResponse

interface ArtistRepository {
    suspend fun getArtist(id: String): Result<ArtistResponse>
}