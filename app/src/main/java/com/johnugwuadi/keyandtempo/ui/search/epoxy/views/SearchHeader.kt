package com.johnugwuadi.keyandtempo.ui.search.epoxy.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.*
import com.johnugwuadi.keyandtempo.R

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class SearchHeader @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val header: TextView

    init {
        View.inflate(context, R.layout.search_result_header, this)
        header = findViewById(R.id.header)
    }

    @TextProp
    fun setHeader(text: CharSequence) {
        header.text = text
    }
}