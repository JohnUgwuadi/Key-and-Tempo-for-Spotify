package com.johnugwuadi.keyandtempo.data.repositories.track

import com.johnugwuadi.keyandtempo.data.repositories.Result
import com.johnugwuadi.keyandtempo.data.local.track.Track

interface TrackRepository {
    suspend fun analyzeTrack(id: String): Result<Track>
}