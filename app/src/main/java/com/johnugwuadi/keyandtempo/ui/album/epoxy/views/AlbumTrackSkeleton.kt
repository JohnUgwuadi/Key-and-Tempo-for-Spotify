package com.johnugwuadi.keyandtempo.ui.album.epoxy.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.ModelView
import com.johnugwuadi.keyandtempo.R

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class AlbumTrackSkeleton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    init {
        View.inflate(context, R.layout.album_track_skeleton, this)
    }
}