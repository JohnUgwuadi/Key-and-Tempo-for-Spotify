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
class ArtistAlbum @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val albumImage: ImageView
    private val albumName: TextView
    private val albumType: TextView
    private val root: ConstraintLayout

    init {
        View.inflate(context, R.layout.artist_item_album, this)
        albumImage = findViewById(R.id.artist_album_image)
        albumName = findViewById(R.id.artist_album_name)
        albumType = findViewById(R.id.artist_album_type)
        root = findViewById(R.id.artist_album_root)
    }

    @TextProp
    fun setName(text: CharSequence) {
        albumName.text = text
    }

    @TextProp
    fun setType(text: CharSequence) {
        albumType.text = text
    }

    @TextProp
    fun setImage(url: CharSequence?) {
        Glide.with(this)
            .load(url)
            .placeholder(R.drawable.ic_baseline_music_note_24)
            .into(albumImage)
    }

    @CallbackProp
    fun setOnClick(listener: OnClickListener?) {
        root.setOnClickListener(listener)
    }
}