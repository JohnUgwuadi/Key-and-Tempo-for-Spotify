package com.johnugwuadi.keyandtempo.ui.track

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.snackbar.Snackbar
import com.johnugwuadi.keyandtempo.R
import com.johnugwuadi.keyandtempo.createGradientFromBitmap
import com.johnugwuadi.keyandtempo.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.track.*

@AndroidEntryPoint
class TrackFragment : Fragment() {
    private val viewModel: TrackViewModel by viewModels()

    private var imageUrl: String? = null
    private lateinit var id: String
    private lateinit var name: String
    private lateinit var artists: ArrayList<String>

    private val fadeInDuration = 160L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.arguments?.let {
            imageUrl = it.getString("imageUrl")
            id = it.getString("id") ?: throw IllegalArgumentException("Track id required.")

            name = it.getString("name") ?: throw IllegalArgumentException("Track name required.")

            artists = it.getStringArrayList("artists")
                ?: throw IllegalArgumentException("Track artist required.")
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.track, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(this)
            .asBitmap()
            .load(imageUrl)
            .placeholder(R.drawable.ic_baseline_music_note_24)
            .addListener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    isFirstResource: Boolean
                ): Boolean {
                    track_image.animate().setDuration(fadeInDuration).alpha(1f).start()
                    return false
                }

                override fun onResourceReady(
                    resource: Bitmap?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    resource?.let {
                        track_background.background =
                            createGradientFromBitmap(it, Color.argb(255, 20, 20, 20))
                        track_background.animate().setDuration(fadeInDuration).alpha(1f).start()
                        track_image.animate().setDuration(fadeInDuration).alpha(1f).start()
                    }
                    return false
                }
            })
            .into(track_image)

        track_name.text = name
        track_artist.text = artists.joinToString()
        toolbar.title = "Track Analysis"
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        viewModel.analysisResponse.observe(viewLifecycleOwner, Observer {
            hideSkeletons()

            track_tempo.alpha = 0f
            track_tempo.text = it.tempo.toString()
            track_tempo.animate().setDuration(fadeInDuration).alpha(1f).start()

            track_key.alpha = 0f
            track_key.text = it.key
            track_key.animate().setDuration(fadeInDuration).alpha(1f).start()
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {
            hideSkeletons()
            Snackbar.make(requireView(), it, Snackbar.LENGTH_LONG).show()
        })

        viewModel.search(id)
        hideKeyboard(requireActivity())
    }

    private fun hideSkeletons() {
        track_tempo_skeleton.visibility = View.GONE
        track_key_skeleton.visibility = View.GONE
    }
}