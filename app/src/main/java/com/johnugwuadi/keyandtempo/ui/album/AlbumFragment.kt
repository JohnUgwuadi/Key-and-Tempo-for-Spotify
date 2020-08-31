package com.johnugwuadi.keyandtempo.ui.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.johnugwuadi.keyandtempo.R
import com.johnugwuadi.keyandtempo.ui.album.epoxy.AlbumController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.album.*

@AndroidEntryPoint
class AlbumFragment : Fragment() {
    private val viewModel: AlbumViewModel by viewModels()

    private var imageUrl: String? = null
    private lateinit var id: String
    private lateinit var name: String
    private lateinit var artists: String
    private lateinit var controller: AlbumController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageUrl = arguments?.getString("imageUrl")

        id = arguments?.getString("id")
            ?: throw IllegalArgumentException("Album id required.")

        name = arguments?.getString("name")
            ?: throw IllegalArgumentException("Album name required.")

        artists = arguments?.getString("artists")
            ?: throw IllegalArgumentException("Album artists required.")

        controller = AlbumController(parentFragmentManager)
        controller.setData(imageUrl, name, artists, null)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.album, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        epoxy_album_recycler.setController(controller)
        toolbar.title = name
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        viewModel.tracks.observe(viewLifecycleOwner, Observer {
            controller.setData(imageUrl, name, artists, it)
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { errorMsg ->
            Snackbar.make(this.requireView(), errorMsg, Snackbar.LENGTH_LONG).show()
        })

        viewModel.loadTracks(id)
    }
}