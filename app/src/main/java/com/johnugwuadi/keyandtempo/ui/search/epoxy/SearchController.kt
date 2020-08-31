package com.johnugwuadi.keyandtempo.ui.search.epoxy

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.airbnb.epoxy.TypedEpoxyController
import com.johnugwuadi.keyandtempo.R
import com.johnugwuadi.keyandtempo.ui.album.AlbumFragment
import com.johnugwuadi.keyandtempo.ui.artist.ArtistFragment
import com.johnugwuadi.keyandtempo.data.remote.models.SearchResult
import com.johnugwuadi.keyandtempo.ui.search.epoxy.views.searchHeader
import com.johnugwuadi.keyandtempo.ui.search.epoxy.views.searchItem
import com.johnugwuadi.keyandtempo.ui.search.epoxy.views.searchSkeleton
import com.johnugwuadi.keyandtempo.ui.track.TrackFragment

class SearchController(
    private val fragmentManager: FragmentManager
) : TypedEpoxyController<SearchResult?>() {
    override fun buildModels(data: SearchResult?) {
        if (data == null) {
            searchHeader {
                id("tracks")
                header("Tracks")
            }

            searchSkeleton { id("skeleton 1") }
            searchSkeleton { id("skeleton 2") }
            searchSkeleton { id("skeleton 3") }

            searchHeader {
                id("Artists")
                header("Artists")
            }

            searchSkeleton { id("skeleton 4") }
            searchSkeleton { id("skeleton 5") }
            searchSkeleton { id("skeleton 6") }

            searchHeader {
                id("Albums")
                header("Albums")
            }

            searchSkeleton { id("skeleton 7") }
            searchSkeleton { id("skeleton 8") }
            searchSkeleton { id("skeleton 9") }
        } else {
            if (data.tracks.isNotEmpty()) {
                searchHeader {
                    id("tracks")
                    header("Tracks")
                }
            }
            data.tracks.forEach { track ->
                searchItem {
                    id(track.id)
                    title(track.name)
                    subtitle(track.artists[0].name)
                    imageByUrl(track.album.images.lastOrNull()?.url)
                    onClick(View.OnClickListener {
                        openTrackFragment(track)
                    })
                }
            }

            if (data.artists.isNotEmpty()) {
                searchHeader {
                    id("Artists")
                    header("Artists")
                }
            }

            data.artists.forEach { artist ->
                searchItem {
                    id(artist.id)
                    title(artist.name)
                    subtitle("Artist")
                    imageByUrl(artist.images.lastOrNull()?.url)
                    onClick(View.OnClickListener {
                        openArtistFragment(artist)
                    })
                }
            }
            if (data.albums.isNotEmpty()) {
                searchHeader {
                    id("Albums")
                    header("Albums")
                }
            }
            data.albums.forEach { album ->
                searchItem {
                    id(album.id)
                    title(album.name)
                    subtitle(album.artists[0].name)
                    imageByUrl(album.images.lastOrNull()?.url)
                    onClick(View.OnClickListener {
                        openAlbumFragment(album)
                    })
                }
            }
        }
    }

    private fun openTrackFragment(track: SearchResult.Track) {
        val trackFragment = TrackFragment()

        val trackData = Bundle()
        trackData.putString("id", track.id)
        trackData.putString("name", track.name)

        val artists = ArrayList(track.artists.map { it.name })
        trackData.putStringArrayList("artists", artists)

        trackData.putString("imageUrl", track.album.images.firstOrNull()?.url)
        trackFragment.arguments = trackData

        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, trackFragment)
            .addToBackStack("Track ${track.id}")
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }

    private fun openArtistFragment(artist: SearchResult.Artist) {
        val artistFragment = ArtistFragment()

        val artistData = Bundle()
        artistData.putString("id", artist.id)
        artistData.putString("name", artist.name)
        artistData.putString("imageUrl", artist.images.firstOrNull()?.url)
        artistFragment.arguments = artistData

        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, artistFragment)
            .addToBackStack("Track ${artist.id}")
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }

    private fun openAlbumFragment(album: SearchResult.Album) {
        val albumFragment = AlbumFragment()

        val albumData = Bundle()
        albumData.putString("id", album.id)
        albumData.putString("name", album.name)
        albumData.putString("imageUrl", album.images.firstOrNull()?.url)
        albumData.putString("artists", album.artists.joinToString { it.name })
        albumFragment.arguments = albumData

        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, albumFragment)
            .addToBackStack("Album ${album.id}")
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }
}