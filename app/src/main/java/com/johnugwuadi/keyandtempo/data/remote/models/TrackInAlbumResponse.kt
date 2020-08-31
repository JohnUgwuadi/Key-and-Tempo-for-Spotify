package com.johnugwuadi.keyandtempo.data.remote.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TrackInAlbumResponse(
    val id: String,
    val name: String,
    val artists: List<ArtistResponse.SimpleArtist>,
    val track_number: Int
)