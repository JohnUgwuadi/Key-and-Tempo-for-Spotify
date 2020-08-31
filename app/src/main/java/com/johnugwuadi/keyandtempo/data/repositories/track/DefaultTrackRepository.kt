package com.johnugwuadi.keyandtempo.data.repositories.track

import com.johnugwuadi.keyandtempo.data.repositories.Result
import com.johnugwuadi.keyandtempo.data.local.track.Track
import com.johnugwuadi.keyandtempo.data.local.track.TrackDao
import com.johnugwuadi.keyandtempo.data.remote.SpotifyService
import com.johnugwuadi.keyandtempo.handleRetrofitErrors
import javax.inject.Inject

class DefaultTrackRepository @Inject constructor(
    private val spotifyService: SpotifyService,
    private val trackDao: TrackDao
) : TrackRepository {
    override suspend fun analyzeTrack(id: String): Result<Track> {
        trackDao.get(id)?.let {
            return Result.Success(it)
        }

        val spotifyResponse = handleRetrofitErrors { spotifyService.analyzeTrack(id) }

        return when (spotifyResponse) {
            is Result.Success -> {
                val track = Track(
                    id,
                    spotifyResponse.data.key,
                    spotifyResponse.data.mode,
                    spotifyResponse.data.tempo
                )
                trackDao.insert(track)
                Result.Success(track)
            }
            is Result.Error -> spotifyResponse
        }
    }
}