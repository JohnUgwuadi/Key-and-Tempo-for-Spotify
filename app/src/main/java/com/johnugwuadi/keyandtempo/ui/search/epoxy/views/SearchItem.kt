package com.johnugwuadi.keyandtempo.ui.search.epoxy.views

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.bumptech.glide.Glide
import com.johnugwuadi.keyandtempo.R

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class SearchItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val title: TextView
    private val subtitle: TextView
    private val image: ImageView
    private val root: ConstraintLayout

    init {
        inflate(context, R.layout.search_result_item, this)
        title = findViewById(R.id.item_title)
        subtitle = findViewById(R.id.item_subtitle)
        image = findViewById(R.id.item_image)
        root = findViewById(R.id.item_root)
    }

    @TextProp
    fun setTitle(text: CharSequence) {
        title.text = text
    }

    @TextProp
    fun setSubtitle(text: CharSequence?) {
        subtitle.text = text
    }

    @TextProp
    fun setImageByUrl(url: CharSequence?) {
        Glide.with(this)
            .load(url)
            .placeholder(R.drawable.ic_baseline_music_note_24)
            .into(image)
    }

    @CallbackProp
    fun setOnClick(onClickListener: OnClickListener?) {
        root.setOnClickListener(onClickListener)
    }
}