package com.johnugwuadi.keyandtempo.ui

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.johnugwuadi.keyandtempo.R

class SkeletonView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    init {
        setBackgroundResource(R.drawable.skeleton)
        pulse()
    }

    private fun pulse() {
        ObjectAnimator.ofFloat(this, "alpha", 0.3f, 1.0f).apply {
            repeatMode = ObjectAnimator.REVERSE
            repeatCount = ObjectAnimator.INFINITE
            interpolator = LinearInterpolator()
            this.duration = 500L
            start()
        }
    }
}