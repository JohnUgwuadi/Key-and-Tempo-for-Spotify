package com.johnugwuadi.keyandtempo.data.remote.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchResult(
    val albums: List<Album>,
    val artists: List<Artist>,
    val tracks: List<Track>
) {
    fun isNotEmpty(): Boolean {
        return !(albums.isEmpty() && tracks.isEmpty() && artists.isEmpty())
    }

    @JsonClass(generateAdapter = true)
    data class Album(
        val artists: List<Artist>,
        val id: String,
        val images: List<Image>,
        val name: String
    ) {
        @JsonClass(generateAdapter = true)
        data class Artist(
            val id: String,
            val name: String
        )

        @JsonClass(generateAdapter = true)
        data class Image(
            val height: Int?,
            val url: String,
            val width: Int?
        )
    }

    @JsonClass(generateAdapter = true)
    data class Artist(
        val id: String,
        val images: List<Image>,
        val name: String,
        val popularity: Int
    ) {

        @JsonClass(generateAdapter = true)
        data class Image(
            val height: Int,
            val url: String,
            val width: Int
        )
    }

    @JsonClass(generateAdapter = true)
    data class Track(
        val album: Album,
        val artists: List<Artist>,
        val id: String,
        val name: String
    ) {

        @JsonClass(generateAdapter = true)
        data class Album(
            val id: String,
            val images: List<Image>,
            val name: String
        )

        @JsonClass(generateAdapter = true)
        data class Image(
            val height: Int,
            val url: String,
            val width: Int
        )

        @JsonClass(generateAdapter = true)
        data class Artist(
            val id: String,
            val name: String
        )
    }
}