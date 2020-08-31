@file:Suppress("unused")

package com.johnugwuadi.keyandtempo.di

import com.johnugwuadi.keyandtempo.data.repositories.album.AlbumRepository
import com.johnugwuadi.keyandtempo.data.repositories.album.DefaultAlbumRepository
import com.johnugwuadi.keyandtempo.data.repositories.artists.ArtistRepository
import com.johnugwuadi.keyandtempo.data.repositories.artists.DefaultArtistRepository
import com.johnugwuadi.keyandtempo.data.repositories.search.DefaultSearchRepository
import com.johnugwuadi.keyandtempo.data.repositories.search.SearchRepository
import com.johnugwuadi.keyandtempo.data.repositories.track.DefaultTrackRepository
import com.johnugwuadi.keyandtempo.data.repositories.track.TrackRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindSearchRepository(
        defaultSearchRepository: DefaultSearchRepository
    ): SearchRepository

    @Singleton
    @Binds
    abstract fun bindArtistRepository(
        artistRepository: DefaultArtistRepository
    ): ArtistRepository

    @Singleton
    @Binds
    abstract fun bindAlbumRepository(
        artistRepository: DefaultAlbumRepository
    ): AlbumRepository

    @Singleton
    @Binds
    abstract fun bindTrackRepository(
        trackRepository: DefaultTrackRepository
    ): TrackRepository
}