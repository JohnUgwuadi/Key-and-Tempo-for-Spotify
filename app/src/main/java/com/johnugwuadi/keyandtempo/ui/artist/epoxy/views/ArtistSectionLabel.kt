package com.johnugwuadi.keyandtempo.ui.artist.epoxy.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.johnugwuadi.keyandtempo.R

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class ArtistSectionLabel @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val sectionName: TextView

    init {
        View.inflate(context, R.layout.artist_section_label, this)
        sectionName = findViewById(R.id.section_name)
    }

    @TextProp
    fun setName(text: CharSequence) {
        sectionName.text = text
    }
}