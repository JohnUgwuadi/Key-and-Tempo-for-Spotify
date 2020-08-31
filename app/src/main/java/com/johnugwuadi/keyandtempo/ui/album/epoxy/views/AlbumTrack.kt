package com.johnugwuadi.keyandtempo.ui.album.epoxy.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.johnugwuadi.keyandtempo.R

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class AlbumTrack @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val trackName: TextView
    private val trackArtists: TextView
    private val root: ConstraintLayout

    init {
        View.inflate(context, R.layout.album_track, this)
        trackName = findViewById(R.id.album_track_name)
        trackArtists = findViewById(R.id.album_track_artists)
        root = findViewById(R.id.album_track_root)
    }

    @TextProp
    fun setName(text: CharSequence) {
        trackName.text = text
    }

    @TextProp
    fun setArtists(text: CharSequence) {
        trackArtists.text = text
    }

    @CallbackProp
    fun setOnClick(onClickListener: OnClickListener?) {
        root.setOnClickListener(onClickListener)
    }
}