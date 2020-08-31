package com.johnugwuadi.keyandtempo.ui.artist.epoxy

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.airbnb.epoxy.TypedEpoxyController
import com.johnugwuadi.keyandtempo.R
import com.johnugwuadi.keyandtempo.ui.album.AlbumFragment
import com.johnugwuadi.keyandtempo.ui.artist.epoxy.views.artistAlbum
import com.johnugwuadi.keyandtempo.ui.artist.epoxy.views.artistSectionLabel
import com.johnugwuadi.keyandtempo.ui.artist.epoxy.views.artistTopTrack
import com.johnugwuadi.keyandtempo.data.remote.models.ArtistResponse
import com.johnugwuadi.keyandtempo.ui.artist.epoxy.views.artistTopTrackSkeleton
import com.johnugwuadi.keyandtempo.ui.track.TrackFragment

class ArtistController(
    private val fragmentManager: FragmentManager
) : TypedEpoxyController<ArtistResponse>() {

    override fun buildModels(data: ArtistResponse?) {
        if (data == null) showSkeleton() else showLoadedData(data)
    }

    private fun showLoadedData(data: ArtistResponse) {
        val albums = data.albums.albums
        var showAlbumLabel = false
        var showSingleLabel = false
        for (album in albums) {
            if ((album.album_type == "single")) {
                showSingleLabel = true
            }
            if (album.album_type == "album") {
                showAlbumLabel = true
            }
        }

        artistSectionLabel {
            id("top_tracks")
            name("Top tracks")
        }

        for ((index, track) in data.topTracks.withIndex()) {
            artistTopTrack {
                id(track.id)
                this.name(track.name)
                image(track.images.firstOrNull()?.url)
                position((index + 1).toString())
                onClick(View.OnClickListener {
                    openTrackFragment(track)
                })
            }
        }

        if (showAlbumLabel) {
            artistSectionLabel {
                id("album_section")
                name("Albums")
            }
        }

        albums.forEach { album ->
            if (album.album_type == "album") {
                artistAlbum {
                    id(album.id)
                    name(album.name)
                    type("Album")
                    image(album.images.firstOrNull()?.url)
                    onClick(View.OnClickListener {
                        openAlbumFragment(album)
                    })
                }
            }
        }

        if (showSingleLabel) {
            artistSectionLabel {
                id("singles_section")
                name("Singles")
            }
        }

        albums.forEach { single ->
            if (single.album_type.contentEquals("single")) {
                artistAlbum {
                    id(single.id)
                    name(single.name)
                    type("Single")
                    image(single.images.firstOrNull()?.url)
                    onClick(View.OnClickListener {
                        openAlbumFragment(single)
                    })
                }
            }
        }
    }

    private fun showSkeleton() {
        artistSectionLabel {
            id("top_tracks")
            name("Top tracks")
        }
        for (i in 1..5) {
            artistTopTrackSkeleton {
                id("top track skeleton $i")
                position("$i")
            }
        }
    }

    private fun openTrackFragment(track: ArtistResponse.Track) {
        val trackFragment = TrackFragment()

        val trackData = Bundle()
        trackData.putString("id", track.id)
        trackData.putString("name", track.name)

        val artists = ArrayList(track.artists.map { it.name })
        trackData.putStringArrayList("artists", artists)

        trackData.putString("imageUrl", track.images.first().url)
        trackFragment.arguments = trackData

        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, trackFragment)
            .addToBackStack("Track ${track.id}")
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }

    private fun openAlbumFragment(album: ArtistResponse.Albums.Album) {
        val albumFragment = AlbumFragment()

        val albumData = Bundle()
        albumData.putString("id", album.id)
        albumData.putString("name", album.name)
        albumData.putString("imageUrl", album.images.firstOrNull()?.url)
        Log.e("Artists", album.artists.joinToString { it.name })
        albumData.putString("artists", album.artists.joinToString { it.name })
        albumFragment.arguments = albumData

        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, albumFragment)
            .addToBackStack("Album ${album.id}")
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }
}