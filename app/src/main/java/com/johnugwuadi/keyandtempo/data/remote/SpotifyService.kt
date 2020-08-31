package com.johnugwuadi.keyandtempo.data.remote

import com.johnugwuadi.keyandtempo.data.remote.models.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SpotifyService {
    @GET("/search")
    suspend fun search(
        @Query("q") query: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): SearchResult

    @GET("/analysis")
    suspend fun analyzeTrack(
        @Query("id") id: String
    ): AnalysisResponse

    @GET("/artists/{id}")
    suspend fun artist(
        @Path("id") id: String
    ): ArtistResponse

    @GET("/albums/{id}")
    suspend fun getTracksInAlbum(
        @Path("id") albumId: String
    ): List<TrackInAlbumResponse>
}