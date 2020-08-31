package com.johnugwuadi.keyandtempo.data.remote.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ArtistResponse(
    @Json(name = "top_tracks")
    val topTracks: List<Track>,
    val albums: Albums
) {
    @JsonClass(generateAdapter = true)
    data class Track(
        val id: String,
        val name: String,
        val images: List<Image>,
        val artists: List<SimpleArtist>
    )

    @JsonClass(generateAdapter = true)
    data class Albums(
        val total: Int,
        @Json(name = "items")
        val albums: List<Album>
    ) {
        @JsonClass(generateAdapter = true)
        data class Album(
            val id: String,
            val images: List<Image>,
            val name: String,
            val album_type: String,
            val artists: List<SimpleArtist>
        )
    }

    @JsonClass(generateAdapter = true)
    data class Image(
        val url: String
    )

    @JsonClass(generateAdapter = true)
    data class SimpleArtist(
        val name: String,
        val id: String
    )
}