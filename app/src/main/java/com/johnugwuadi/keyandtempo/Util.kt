package com.johnugwuadi.keyandtempo

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorInt
import androidx.core.graphics.ColorUtils
import androidx.palette.graphics.Palette

fun showKeyboard(view: View) {
    val inputMethodManager = view
        .context
        .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    inputMethodManager?.showSoftInput(view.findFocus(), InputMethodManager.SHOW_IMPLICIT)
}

fun hideKeyboard(activity: Activity) {
    val inputMethodManager = activity
        .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    inputMethodManager
        .hideSoftInputFromWindow(
            activity.window.decorView.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
}

fun createGradientFromBitmap(bitmap: Bitmap, @ColorInt endColor: Int): GradientDrawable {
    val palette = Palette.Builder(bitmap).generate()
    val color = palette.getDominantColor(Color.WHITE)
    return GradientDrawable().apply {
        gradientType = GradientDrawable.LINEAR_GRADIENT
        val topOfGradientColor = ColorUtils.blendARGB(color, Color.DKGRAY, 0.7f)
        colors = intArrayOf(topOfGradientColor, endColor)
    }
}