package com.johnugwuadi.keyandtempo.ui.album.epoxy.views

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.johnugwuadi.keyandtempo.R
import com.johnugwuadi.keyandtempo.createGradientFromBitmap

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class AlbumHeader @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val albumName: TextView
    private val albumArtist: TextView
    private val albumImage: ImageView
    private val background: View

    private val fadeInDuration = 200L

    init {
        View.inflate(context, R.layout.album_header, this)
        albumName = findViewById(R.id.album_name)
        albumArtist = findViewById(R.id.album_artist)
        albumImage = findViewById(R.id.album_image)
        background = findViewById(R.id.background)
    }

    @TextProp
    fun setName(text: CharSequence) {
        albumName.text = text
    }

    @TextProp
    fun setArtist(text: CharSequence) {
        albumArtist.text = text
    }

    @TextProp
    fun setImage(url: CharSequence?) {
        Glide.with(this)
            .asBitmap()
            .placeholder(R.drawable.ic_baseline_music_note_24)
            .addListener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    isFirstResource: Boolean
                ): Boolean {
                    albumImage.animate().alpha(1f).setDuration(fadeInDuration).start()
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
                        background.background = createGradientFromBitmap(
                            it,
                            Color.argb(0, 0, 0, 0)
                        )
                        background.animate().alpha(1f).setDuration(fadeInDuration).start()
                        albumImage.animate().alpha(1f).setDuration(fadeInDuration).start()
                    }
                    return false
                }
            })
            .load(url)
            .into(albumImage)
    }
}