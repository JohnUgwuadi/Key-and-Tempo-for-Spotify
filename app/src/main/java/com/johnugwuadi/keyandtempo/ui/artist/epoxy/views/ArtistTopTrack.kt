package com.johnugwuadi.keyandtempo.ui.artist.epoxy.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.bumptech.glide.Glide
import com.johnugwuadi.keyandtempo.R

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class ArtistTopTrack @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val trackName: TextView
    private val trackPosition: TextView
    private val trackImage: ImageView
    private val root: ConstraintLayout

    init {
        View.inflate(context, R.layout.artist_item_track, this)
        trackName = findViewById(R.id.artist_track_name)
        trackPosition = findViewById(R.id.artist_track_position)
        trackImage = findViewById(R.id.artist_track_image)
        root = findViewById(R.id.artist_track_root)
    }

    @TextProp
    fun setName(text: CharSequence) {
        trackName.text = text
    }

    @TextProp
    fun setPosition(text: CharSequence) {
        trackPosition.text = text
    }

    @TextProp
    fun setImage(url: CharSequence?) {
        Glide.with(this)
            .load(url)
            .placeholder(R.drawable.ic_baseline_music_note_24)
            .into(trackImage)
    }

    @CallbackProp
    fun setOnClick(onClickListener: OnClickListener?) {
        root.setOnClickListener(onClickListener)
    }
}