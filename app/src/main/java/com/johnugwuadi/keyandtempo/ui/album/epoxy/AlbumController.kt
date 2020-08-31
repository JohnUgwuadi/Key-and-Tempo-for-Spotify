package com.johnugwuadi.keyandtempo.ui.album.epoxy

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.airbnb.epoxy.Typed4EpoxyController
import com.johnugwuadi.keyandtempo.R
import com.johnugwuadi.keyandtempo.ui.album.epoxy.views.albumHeader
import com.johnugwuadi.keyandtempo.ui.album.epoxy.views.albumTrack
import com.johnugwuadi.keyandtempo.data.remote.models.TrackInAlbumResponse
import com.johnugwuadi.keyandtempo.ui.album.epoxy.views.albumTrackSkeleton
import com.johnugwuadi.keyandtempo.ui.track.TrackFragment

class AlbumController(private val fragmentManager: FragmentManager) :
    Typed4EpoxyController<String, String, String, List<TrackInAlbumResponse>>() {

    override fun buildModels(
        imageUrl: String?,
        name: String,
        artists: String,
        tracksResponse: List<TrackInAlbumResponse>?
    ) {
        albumHeader {
            id("album_header")
            name(name)
            artist(artists)
            image(imageUrl)
        }

        if (tracksResponse == null) {
            for (i in 0..10) {
                albumTrackSkeleton {
                    id("Skeleton $i")
                }
            }
        } else {
            for (track in tracksResponse) {
                albumTrack {
                    id(track.id)
                    name(track.name)
                    val artists = track.artists.map { it.name }
                    artists(artists.joinToString())
                    onClick(View.OnClickListener {
                        openTrackFragment(track, imageUrl)
                    })
                }
            }
        }
    }

    private fun openTrackFragment(track: TrackInAlbumResponse, imageUrl: String?) {
        val trackFragment = TrackFragment()

        val trackData = Bundle()
        trackData.putString("id", track.id)
        trackData.putString("name", track.name)

        val artists = ArrayList(track.artists.map { it.name })
        trackData.putStringArrayList("artists", artists)

        trackData.putString("imageUrl", imageUrl)
        trackFragment.arguments = trackData

        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, trackFragment)
            .addToBackStack("Track ${track.id}")
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }
}