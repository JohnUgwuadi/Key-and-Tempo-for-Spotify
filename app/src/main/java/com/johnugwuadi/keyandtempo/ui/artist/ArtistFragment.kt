package com.johnugwuadi.keyandtempo.ui.artist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar
import com.johnugwuadi.keyandtempo.R
import com.johnugwuadi.keyandtempo.ui.artist.epoxy.ArtistController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.artist.*

@AndroidEntryPoint
class ArtistFragment : Fragment() {
    private val viewModel: ArtistViewModel by viewModels()

    private var imageUrl: String? = null
    private lateinit var name: String
    private lateinit var id: String
    private lateinit var controller: ArtistController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            imageUrl = it.getString("imageUrl")

            name = it.getString("name") ?: throw IllegalArgumentException("Artist name required.")

            id = it.getString("id") ?: throw IllegalArgumentException("Artist id required.")
        }

        controller = ArtistController(parentFragmentManager)
        controller.setData(null)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.artist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        epoxy_artist_recycler.setController(controller)

        Glide.with(this)
            .load(imageUrl)
            .placeholder(R.drawable.default_artist_image)
            .into(artist_image)

        toolbar_title.text = name
        artist_name.text = name

        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        viewModel.artistData.observe(viewLifecycleOwner, Observer {
            controller.setData(it)
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {
            Snackbar.make(requireView(), it, Snackbar.LENGTH_LONG).show()
        })

        viewModel.load(id)

        appbar_layout.addOnOffsetChangedListener(
            AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                val scrollRange = appBarLayout.totalScrollRange

                //Fades in title as user scrolls down page
                toolbar_title.alpha = 1 - ((scrollRange + verticalOffset).toFloat() / scrollRange)

                //Fades out artist image
                artist_image.alpha = ((scrollRange + verticalOffset).toFloat() / scrollRange)
            })
    }
}